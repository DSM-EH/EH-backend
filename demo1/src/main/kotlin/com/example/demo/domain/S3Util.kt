package com.example.demo.domain

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.internal.Mimetypes
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.example.demo.global.exception.InternalServerErrorException
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.io.File
import java.io.IOException

@Component
class S3Util(
    private val amazonS3Client: AmazonS3Client
) {

    companion object {
        val BUCKET = "eh-s3"
    }

    fun upload(file: File): String {
        inputS3(file, file.name)

        return getResource(file.name)
    }

    private fun inputS3(file: File, fileName: String) {
        try {
            val inputStream = file.inputStream()
            val objectMetadata = ObjectMetadata().apply {
                this.contentLength = file.length()
                this.contentType = Mimetypes.getInstance().getMimetype(file)
            }

            amazonS3Client.putObject(
                PutObjectRequest(BUCKET, fileName, inputStream, objectMetadata)
                    .withCannedAcl(
                        CannedAccessControlList.PublicRead
                    )
            )

            file.delete()
        } catch (e: IOException) {
            throw InternalServerErrorException
        }
    }

    private fun getResource(fileName: String): String {
        return amazonS3Client.getResourceUrl(BUCKET, fileName)
    }

}