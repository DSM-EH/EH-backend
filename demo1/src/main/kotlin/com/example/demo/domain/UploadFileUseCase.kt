package com.example.demo.domain

import com.example.demo.global.exception.InternalServerErrorException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

@RestController
class UploadFileUseCase(
    private val s3Util: S3Util
) {

    data class UploadFileResponse(
        val fileUrl: String
    )

    @PostMapping("/image")
    fun uploadFile(@RequestPart file: MultipartFile): UploadFileResponse {
        val result = execute(
            file.let(transferFile)
        )

        return UploadFileResponse(result)
    }

    private val transferFile = { multipartFile: MultipartFile ->
        val fileName = "${UUID.randomUUID()}_${multipartFile.originalFilename}"
        File(fileName).let {
            FileOutputStream(it).run {
                this.write(multipartFile.bytes)
                this.close()
            }
            it
        }
    }

    fun execute(file: File): String {
        if (!file.isCorrectExtension(file)) {
            file.delete()
            throw InternalServerErrorException
        }

        return s3Util.upload(file)
    }

    internal fun File.isCorrectExtension(file: File) = when (file.extension.lowercase()) {
        "jpg", "jpeg", "png", "heic" -> true
        else -> false
    }
}