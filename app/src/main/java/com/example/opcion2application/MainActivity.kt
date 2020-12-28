package com.example.opcion2application

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginBehavior
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.security.auth.callback.Callback


class MainActivity : AppCompatActivity() {
    var googleSignInClient : GoogleSignInClient? = null
    var llamadaSignIngoogle = 1000
    var callBackManager= CallbackManager.Factory.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSignUp.setOnClickListener {
            crearEmailfire()
            Toast.makeText(this,"usuario registrado",Toast.LENGTH_SHORT).show()
            editTextEmail.setText("")
            editTextPassword.setText("")
        }

        var gso  = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient( this, gso)

        btnGooglesigup.setOnClickListener {
            var signInIntent = googleSignInClient?.signInIntent
            startActivityForResult(signInIntent, llamadaSignIngoogle)
        }
        printHashKey(this)

        btnLogin.setOnClickListener {
            loginEmail()
        }
    }
    //-------------------------------------------------------------------------------------------------
    override fun onPostResume() {
        super.onPostResume()
        nexpagina()
    }
    fun nexpagina(){
        var curentUser = FirebaseAuth.getInstance().currentUser
        if (curentUser != null){
            startActivity(Intent(this,control::class.java))
        }
    }
    //--------------------------------------------------------------------------------------------------
        fun facebooklogin(){
            LoginManager.getInstance().loginBehavior = LoginBehavior.WEB_VIEW_ONLY
            LoginManager.getInstance().logInWithPublishPermissions(this, Arrays.asList("public profile","email"))
            LoginManager.getInstance().registerCallback(callBackManager,object :FacebookCallback<LoginResult>{
                override fun onSuccess(result: LoginResult?) {
                    FirebaseAuthWithFacebook(result)
                }
                override fun onCancel() {
                }
                override fun onError(error: FacebookException?) {
                }
            })
        }
     fun FirebaseAuthWithFacebook (result: LoginResult?){
         var credential= FacebookAuthProvider.getCredential(result?.accessToken?.token!!)
         FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener { task ->
             if (task.isSuccessful){
                 nexpagina()
             }
         }

     }
    fun printHashKey(pContext: Context) {
        try {
            val info: PackageInfo = pContext.getPackageManager()
                .getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = String(Base64.encode(md.digest(), 0))
                println( "printHashKey() Hash Key: $hashKey")
            }
        } catch (e: NoSuchAlgorithmException) {
        } catch (e: Exception) {
        }
    }
    //-------------------------------------------------------------------------------------------------
    fun crearEmailfire (){
        var email = editTextEmail.text.toString()
        var password = editTextPassword.text.toString()

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful)
                startActivity(Intent(this, MainActivity::class.java))
        }
    }
    fun loginEmail(){
        try {


        var email = editTextEmail.text.toString()
        var password = editTextPassword.text.toString()

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful)
                nexpagina()
        }
        } catch (e:NullPointerException){
            Toast.makeText(this,"no se ha registrado correctamente ",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
    //-------------------------------------------------------------------------------------------------------
    fun firebaseAuthWithGoogle(acc: GoogleSignInAccount?){
        try {

        val credential = GoogleAuthProvider.getCredential(acc?.idToken,null)
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                task ->
            if (task.isSuccessful){
                nexpagina()
            }
        }
        } catch (e : NullPointerException){
            Toast.makeText(this,"no se registro correctamente", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callBackManager.onActivityResult(requestCode,resultCode,data)
        if (requestCode==llamadaSignIngoogle){
            var task = GoogleSignIn.getSignedInAccountFromIntent(data)
            var account = task.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account)
        }
    }
}