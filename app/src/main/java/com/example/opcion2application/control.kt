package com.example.opcion2application
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_control.*
class control : AppCompatActivity() {
    // referencia a base de datos
    //var database = FirebaseDatabase.getInstance()
    //var myRef = database.getReference("message")
    var googleSignInClient : GoogleSignInClient?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control)

        //----------cerrar sesion -------------
        btncerrar.setOnClickListener {
            logout()
        }
        //--------------------------dependencia de google para poder cerrar sesion
        var gso  = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient( this, gso)
        //****************************************************************************
        Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
        //llama a las funciones de apagar o encender con los respectivos botones
         btnOn.setOnClickListener {onsala()}
         btnOff.setOnClickListener {offsala()}

         btnOncomedor.setOnClickListener { oncomedor() }
         btnOffcomedor.setOnClickListener { offcomedor() }

         btnOndormi.setOnClickListener { ondormitorio() }
         btnOffdormi.setOnClickListener { offdormitorio() }

         btnOnlavanderia.setOnClickListener { onlavanderia() }
         btnOfflavanderia.setOnClickListener { offlavanderia() }

         btnOnpatio.setOnClickListener { onpatio() }
         btnOffpatio.setOnClickListener { offpatio() }
    //--muestra estados actualizados de relay encendidos
        estadosala()
        estadocomedor()
        estadodormitorio()
        estadolavanderia()
        estadopatio()

    }
    //funcion para salir de app
    fun logout(){
        //cerrar secion en correo normal
        FirebaseAuth.getInstance().signOut()
        //cerrar secion en correo de google
        googleSignInClient?.signOut()
        //cerrar secion en correo de facebook
        LoginManager.getInstance().logOut()
        finish()
    }
//*********************************
    //funcion para apagar o encender sala
    fun offsala(){
        var encender = "True"
        var map = mutableMapOf<String,Any>()

        map["luz_sala"]= encender
        FirebaseDatabase.getInstance().reference
            .child("home")
            .child("luces")
            .updateChildren(map)

        actualizasalaoff()
        
    }
    fun onsala(){
        var map = mutableMapOf<String,Any>()
        FirebaseDatabase.getInstance().reference
            .child("home")
            .child("luces")
            .child("luz_sala")
            .removeValue()

        actualizasalaon()
    }
    fun estadosala(){

        FirebaseDatabase.getInstance().reference
            .child("home")
            .child("luces")
            //////---------------------------------------------------------
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {
                    var map = p0.value as Map<String, Any>
                    textestadosala.text = map["luz_sala2"].toString()
                }
            })

    }
    fun actualizasalaon(){
        var encender = "encendido"
        var map = mutableMapOf<String,Any>()

        map["luz_sala2"]= encender
        FirebaseDatabase.getInstance().reference
            .child("home")
            .child("luces")
            .updateChildren(map)


    }
    fun actualizasalaoff(){
        var encender = "apagado"
        var map = mutableMapOf<String,Any>()

        map["luz_sala2"]= encender
        FirebaseDatabase.getInstance().reference
            .child("home")
            .child("luces")
            .updateChildren(map)

    }

//********************************
//funcion para apagar o encender comedor
    fun offcomedor(){
        var encender = "True"
        var map = mutableMapOf<String,Any>()

        map["luz_comedor"]= encender
        FirebaseDatabase.getInstance().reference
            .child("home")
            .child("lucesB")
            .updateChildren(map)

            actualizacomedoroff()
    }
    fun oncomedor(){
        var map = mutableMapOf<String,Any>()

        FirebaseDatabase.getInstance().reference
            .child("home")
            .child("lucesB")
            .child("luz_comedor")
            .removeValue()
        actualizacomedoron()
    }
    fun estadocomedor(){
        FirebaseDatabase.getInstance().reference
            .child("home")
            .child("lucesB")
            //////---------------------------------------------------------
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {
                    var map = p0.value as Map<String, Any>
                    textestadocomedor.text = map["luz_comedor2"].toString()
                }
            })

    }
    fun actualizacomedoron(){
        var encender = "encendido"
        var map = mutableMapOf<String,Any>()

        map["luz_comedor2"]= encender
        FirebaseDatabase.getInstance().reference
            .child("home")
            .child("lucesB")
            .updateChildren(map)

    }
    fun actualizacomedoroff(){
        var encender = "apagado"
        var map = mutableMapOf<String,Any>()

        map["luz_comedor2"]= encender
        FirebaseDatabase.getInstance().reference
            .child("home")
            .child("lucesB")
            .updateChildren(map)
    }

//********************************
//funcion para apagar o encender dormitorio
    fun offdormitorio(){
    var encender = "True"
    var map = mutableMapOf<String,Any>()
    map["luz_dormitorio"]= encender
    FirebaseDatabase.getInstance().reference
        .child("home")
        .child("lucesC")
        .updateChildren(map)
    actualizadormitoriooff()
}
    fun ondormitorio(){
        var map = mutableMapOf<String,Any>()
        FirebaseDatabase.getInstance().reference
            .child("home")
            .child("lucesC")
            .child("luz_dormitorio")
            .removeValue()
        actualizadormitorioon()

    }
    fun estadodormitorio(){
        FirebaseDatabase.getInstance().reference
            .child("home")
            .child("lucesC")
            //////---------------------------------------------------------
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {
                    var map = p0.value as Map<String, Any>
                    textestadoDormitorio.text = map["luz_dormitorio2"].toString()
                }
            })

    }
    fun actualizadormitorioon(){
        var encender = "encendido"
        var map = mutableMapOf<String,Any>()

        map["luz_dormitorio2"]= encender
        FirebaseDatabase.getInstance().reference
            .child("home")
            .child("lucesC")
            .updateChildren(map)

    }
    fun actualizadormitoriooff(){
        var encender = "apagado"
        var map = mutableMapOf<String,Any>()

        map["luz_dormitorio2"]= encender
        FirebaseDatabase.getInstance().reference
            .child("home")
            .child("lucesC")
            .updateChildren(map)
    }
//********************************
//funcion para apagar o encender lavanderia
    fun offlavanderia(){
    var encender = "True"
    var map = mutableMapOf<String,Any>()
    map["luz_lavanderia"]= encender
    FirebaseDatabase.getInstance().reference
        .child("home")
        .child("lucesD")
        .updateChildren(map)
    actualizalavanderiaoff()
}
    fun onlavanderia(){
        var map = mutableMapOf<String,Any>()
        FirebaseDatabase.getInstance().reference
            .child("home")
            .child("lucesD")
            .child("luz_lavanderia")
            .removeValue()

        actualizalavanderiaon()
    }
    fun estadolavanderia(){
        FirebaseDatabase.getInstance().reference
            .child("home")
            .child("lucesD")
            //////---------------------------------------------------------
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {
                    var map = p0.value as Map<String, Any>
                    textestadolavanderia.text = map["luz_lavanderia2"].toString()
                }
            })

    }
    fun actualizalavanderiaon(){
        var encender = "encendido"
        var map = mutableMapOf<String,Any>()

        map["luz_lavanderia2"]= encender
        FirebaseDatabase.getInstance().reference
            .child("home")
            .child("lucesD")
            .updateChildren(map)

    }
    fun actualizalavanderiaoff(){
        var encender = "apagado"
        var map = mutableMapOf<String,Any>()

        map["luz_lavanderia2"]= encender
        FirebaseDatabase.getInstance().reference
            .child("home")
            .child("lucesD")
            .updateChildren(map)
    }
//********************************
//funcion para apagar o encender patio
    fun offpatio(){
    var encender = "True"
    var map = mutableMapOf<String,Any>()
    map["luz_areaverde"]= encender
    FirebaseDatabase.getInstance().reference
        .child("home")
        .child("lucesE")
        .updateChildren(map)
    actualizapatiooff()
}
    fun onpatio(){
        var map = mutableMapOf<String,Any>()
        FirebaseDatabase.getInstance().reference
            .child("home")
            .child("lucesE")
            .child("luz_areaverde")
            .removeValue()
        actualizapatioon()
    }
    fun estadopatio(){
        FirebaseDatabase.getInstance().reference
            .child("home")
            .child("lucesE")
            //////---------------------------------------------------------
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {
                    var map = p0.value as Map<String, Any>
                    textestadopatio.text = map["luz_areaverde2"].toString()
                }
            })

    }
    fun actualizapatioon(){
        var encender = "encendido"
        var map = mutableMapOf<String,Any>()

        map["luz_areaverde2"]= encender
        FirebaseDatabase.getInstance().reference
            .child("home")
            .child("lucesE")
            .updateChildren(map)

    }
    fun actualizapatiooff(){
        var encender = "apagado"
        var map = mutableMapOf<String,Any>()

        map["luz_areaverde2"]= encender
        FirebaseDatabase.getInstance().reference
            .child("home")
            .child("lucesE")
            .updateChildren(map)
    }
//********************************

}