package com.garam.qook.auth

import cocoapods.FirebaseAuth.FIRAuth
import cocoapods.FirebaseAuth.FIROAuthProvider
import cocoapods.FirebaseAuth.FIRUserInfoProtocol
import com.garam.qook.data.local.LocalUserData
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.AuthenticationServices.ASAuthorization
import platform.AuthenticationServices.ASAuthorizationAppleIDCredential
import platform.AuthenticationServices.ASAuthorizationAppleIDProvider
import platform.AuthenticationServices.ASAuthorizationController
import platform.AuthenticationServices.ASAuthorizationControllerDelegateProtocol
import platform.AuthenticationServices.ASAuthorizationControllerPresentationContextProvidingProtocol
import platform.AuthenticationServices.ASAuthorizationScopeEmail
import platform.AuthenticationServices.ASAuthorizationScopeFullName
import platform.Foundation.NSData
import platform.Foundation.NSError
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.create
import platform.UIKit.UIApplication
import platform.UIKit.UIWindow
import platform.darwin.NSObject
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import kotlin.coroutines.resumeWithException

class AuthRepositoryImpl : AuthRepository {

    private var appleAuthDelegate: NSObject? = null
    private var globalAppleDelegate: Any? = null


    override suspend fun signInWithGoogle(
        idToken: String,
        accessToken: String
    ): LocalUserData? = null

    override suspend fun signInWithApple(): LocalUserData? =
        suspendCancellableCoroutine { continuation ->

            val rawNonce = randomNonceString()

            val provider = ASAuthorizationAppleIDProvider()
            val request = provider.createRequest().apply {
                requestedScopes = listOf(ASAuthorizationScopeEmail, ASAuthorizationScopeFullName)
            }


            val controller = ASAuthorizationController(listOf(request))
//            val delegate
//            strongAuthDelegate
            appleAuthDelegate = object : NSObject(),
                ASAuthorizationControllerDelegateProtocol,
                ASAuthorizationControllerPresentationContextProvidingProtocol {

                override fun presentationAnchorForAuthorizationController(controller: ASAuthorizationController): UIWindow {
//                    return UIApplication.sharedApplication.keyWindow
//                        ?: UIApplication.sharedApplication.windows.first() as UIWindow
                    return UIApplication.sharedApplication.windows.first() as UIWindow
                }
//                = platform.UIKit.UIApplication.sharedApplication.keyWindow!!

                override fun authorizationController(
                    controller: ASAuthorizationController,
                    didCompleteWithAuthorization: ASAuthorization
                ) {
                    val credential =
                        didCompleteWithAuthorization.credential as? ASAuthorizationAppleIDCredential
                    val idTokenData: NSData? = credential?.identityToken
                    val idToken =
                        idTokenData?.let { NSString.create(it, NSUTF8StringEncoding) }

                    println("idToken : ${idToken.toString()}")
                    val firebaseCredential =
                        FIROAuthProvider.appleCredentialWithIDToken(
                            idToken.toString(),
                            rawNonce,
                            null
                        )
                    FIRAuth.auth().signInWithCredential(firebaseCredential) { result, error ->

                        if (result != null) {
                            val user = result.user()
                            val email = user.email()
                            val uid = user.uid()
                            val loginType = "apple"

                            println("ios uid : ${uid}")
                            println("ios email : ${email}")

                            println("ios loginType : ${(user.providerData()[0] as? FIRUserInfoProtocol)?.providerID()}")


                            val userData =
                                LocalUserData(email = email, uid = uid, loginType = loginType)

                            continuation.resume(userData) {
                                println(it.message)
                            }

                        } else {
                            println(error?.localizedDescription)
                            continuation.resumeWithException(Exception(error?.localizedDescription))
                        }
                    }


                }

                override fun authorizationController(
                    controller: ASAuthorizationController,
                    didCompleteWithError: NSError
                ) {
                    println("Apple login error: ${didCompleteWithError.localizedDescription}")
                    continuation.resumeWithException(Exception(didCompleteWithError.localizedDescription))
                }
            }

            globalAppleDelegate = appleAuthDelegate

            controller.delegate = appleAuthDelegate as ASAuthorizationControllerDelegateProtocol
            controller.presentationContextProvider =
                appleAuthDelegate as ASAuthorizationControllerPresentationContextProvidingProtocol

            continuation.invokeOnCancellation {
                globalAppleDelegate = null
            }

            dispatch_async(dispatch_get_main_queue()) {
                controller.performRequests()
            }
        }


    override suspend fun signOut() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAccount() {
        TODO("Not yet implemented")
    }

    override fun currentUser(): LocalUserData? {

        val user = FIRAuth.auth().currentUser()

        return if(user == null) null
        else LocalUserData(user.uid(), user.email(), "Apple")

    }

    fun randomNonceString(length: Int = 32): String {
        val charset = "0123456789ABCDEFGHIJKLMNOPQRSTUVXYZabcdefghijklmnopqrstuvwxyz-._"
        val result = StringBuilder()
        var remainingLength = length

        while (remainingLength > 0) {
            val random = (0..charset.lastIndex).random() // 간단한 랜덤 (보안성을 높이려면 SecRandomCopyBytes 권장)
            result.append(charset[random])
            remainingLength--
        }
        return result.toString()
    }
}