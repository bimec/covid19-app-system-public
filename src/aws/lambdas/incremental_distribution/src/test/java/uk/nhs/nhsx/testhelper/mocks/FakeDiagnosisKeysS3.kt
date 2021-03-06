package uk.nhs.nhsx.testhelper.mocks

import com.amazonaws.services.s3.model.S3Object
import com.amazonaws.services.s3.model.S3ObjectSummary
import com.google.common.io.ByteSource
import org.apache.http.entity.ContentType
import uk.nhs.nhsx.core.Jackson
import uk.nhs.nhsx.core.aws.s3.AwsS3
import uk.nhs.nhsx.core.aws.s3.MetaHeader
import uk.nhs.nhsx.core.aws.s3.S3Storage
import uk.nhs.nhsx.diagnosiskeydist.agspec.ENIntervalNumber
import uk.nhs.nhsx.diagnosiskeyssubmission.model.StoredTemporaryExposureKey
import uk.nhs.nhsx.diagnosiskeyssubmission.model.StoredTemporaryExposureKeyPayload
import java.io.ByteArrayInputStream
import java.security.SecureRandom
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

class FakeDiagnosisKeysS3(private val objectSummaries: List<S3ObjectSummary>,
                          private val objectsKeysToSkip: List<String> = listOf()) : AwsS3 {

    override fun upload(locator: S3Storage.Locator?, contentType: ContentType?, bytes: ByteSource?, vararg meta: MetaHeader?) {}

    override fun getObjectSummaries(bucketName: String?) = objectSummaries

    override fun getObject(bucketName: String?, key: String?): Optional<S3Object> {
        if (objectsKeysToSkip.contains(key)) {
            return Optional.empty()
        }
        val mostRecentKeyRollingStart = ENIntervalNumber.enIntervalNumberFromTimestamp(
            Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC))
        ).enIntervalNumber / 144 * 144
        val json = Jackson.toJson(StoredTemporaryExposureKeyPayload(
            listOf(makeKey(mostRecentKeyRollingStart - 144))
        ))
        val s3Object = S3Object()
        s3Object.setObjectContent(ByteArrayInputStream(json.toByteArray()))
        return Optional.of(s3Object)
    }

    override fun deleteObject(bucketName: String?, objectKeyName: String?) {}

    private fun makeKey(keyStartTime: Long): StoredTemporaryExposureKey {
        val key = ByteArray(16)
        SecureRandom().nextBytes(key)
        val base64Key = Base64.getEncoder().encodeToString(key)
        return StoredTemporaryExposureKey(base64Key, Math.toIntExact(keyStartTime), 144, 7)
    }
}