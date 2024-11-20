package com.bkalysh.devicer2.mocked.backend.utils

import org.jose4j.jws.AlgorithmIdentifiers
import org.jose4j.jws.JsonWebSignature
import org.jose4j.jwt.JwtClaims
import org.jose4j.jwt.consumer.JwtConsumerBuilder
import org.jose4j.keys.HmacKey

class JWT {
    companion object {
        fun createJwtToken(userId: String, userName: String): String {
            val secretKey = "devicer-jwt-key-!#@5$&1234567890".toByteArray() // todo move key

            val claims = JwtClaims().apply {
                issuer = "devicer"
                subject = userId
                setClaim("user_name", userName)
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
    }
}