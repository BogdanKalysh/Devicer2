package com.bkalysh.devicer2.mocked.api.utils

import org.jose4j.jws.AlgorithmIdentifiers
import org.jose4j.jws.JsonWebSignature
import org.jose4j.jwt.JwtClaims
import org.jose4j.jwt.consumer.JwtConsumerBuilder
import org.jose4j.keys.HmacKey

class JWT {
    companion object {
        fun createJwtToken(email: String, userName: String): String {
            val secretKey = "devicer-jwt-key-!#@5$&1234567890".toByteArray() // todo move key

            val claims = JwtClaims().apply {
                issuer = "devicer"
                subject = userName
                setClaim("user_name", userName)
                setClaim("email", email)
                setIssuedAtToNow()
                setExpirationTimeMinutesInTheFuture(60f)
            }

            val jws = JsonWebSignature().apply {
                payload = claims.toJson()
                key = HmacKey(secretKey)
                algorithmHeaderValue = AlgorithmIdentifiers.HMAC_SHA256
            }

            return jws.compactSerialization
        }

        fun verifyJwtToken(jwt: String): Boolean {
            val secretKey = "devicer-jwt-key-!#@5$&1234567890".toByteArray() // todo move key

            val jwtConsumer = JwtConsumerBuilder()
                .setRequireExpirationTime()
                .setExpectedIssuer("devicer")
                .setVerificationKey(HmacKey(secretKey))
                .build()

            return try {
                val claims = jwtConsumer.processToClaims(jwt)
                println("JWT validated. Claims: $claims")
                true
            } catch (e: Exception) {
                println("Invalid JWT: ${e.message}")
                false
            }
        }

        fun decodeJwtToken(token: String): Map<String, Any> {
            val secretKey = "devicer-jwt-key-!#@5$&1234567890".toByteArray() // todo move key

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