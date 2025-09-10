package com.bkalysh.devicer2.mocked.api.utils

import com.bkalysh.devicer2.mocked.api.utils.Secrets.secretKey
import org.jose4j.jws.AlgorithmIdentifiers
import org.jose4j.jws.JsonWebSignature
import org.jose4j.jwt.JwtClaims
import org.jose4j.jwt.consumer.JwtConsumerBuilder
import org.jose4j.keys.HmacKey

class JWT {
    companion object {
        fun createJwtToken(email: String, userName: String): String {
            val claims = JwtClaims().apply {
                issuer = "devicer"
                subject = userName
                setClaim("user_name", userName)
                setClaim("email", email)
                setIssuedAtToNow()
                setExpirationTimeMinutesInTheFuture(240f)
            }

            val jws = JsonWebSignature().apply {
                payload = claims.toJson()
                key = HmacKey(secretKey)
                algorithmHeaderValue = AlgorithmIdentifiers.HMAC_SHA256
            }

            return jws.compactSerialization
        }

        fun decodeJwtToken(token: String): Map<String, Any> {
            val jwtConsumer = JwtConsumerBuilder()
                .setRequireExpirationTime()
                .setAllowedClockSkewInSeconds(30)
                .setRequireSubject()
                .setVerificationKey(HmacKey(secretKey))
                .build()

            val jwtClaims = jwtConsumer.processToClaims(token)

            return jwtClaims.claimsMap
        }
    }
}