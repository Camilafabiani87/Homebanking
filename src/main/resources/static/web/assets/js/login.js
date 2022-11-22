const app = Vue.createApp({
    data() {
        return {
          
            email:"",
            contraseña:"",
            primerNombre:"",
            apellido:"",
            emailRegistro:"",
            contraseñaRegistro:"",
            contraseñaRegistroRepetida:"",
            error: "",
            password:"",
            text:"",
            type:"",
           
     
        }
    },

    mounted(){
       
    },

    created() {
        
        
    },
   
    methods: {

       
        ingresarUsuario(){
            axios.post('/api/login',"email="+ this.email + " &password=" + this.contraseña).then(() => window.location.href = '/web/accounts.html')
            .catch((error) =>{this.error = error.respuesta
                if (this.error == "Missing data"){
                    Swal.fire({
                        icon: 'error',
                        title: 'Oops..',
                        text: 'Debes registrarte',
                        
                      })
                } else {
                    Swal.fire({
                        confirmButtonColor: "#1bb5db",
                        background:'#212121',
                        color:'white',
                        icon: 'error',
                        title: 'Email incorrecto, intenta de nuevo ',
                        text: 'Si no estas registrado puedes hacerlo en la opción REGISTRARSE',
                       
                       
                      })
            
                }
            }
            )
            
        },
        mostrarContra(){
            let tipo = document.getElementById("password");
            if (tipo.type == 'password'){
                tipo.type = 'text';
            }else{
                tipo.type = 'password';
            }
        },
        mostrarContra2(){
            let tipo = document.getElementById("pasword");
            if (tipo.type == 'password'){
                tipo.type = 'text';
            }else{
                tipo.type = 'password';
            }
        },
        mostrarContra3(){
            let tipo = document.getElementById("paword");
            if (tipo.type == 'password'){
                tipo.type = 'text';
            }else{
                tipo.type = 'password';
            }
        },

        cambiarForm(){
            let form = document.getElementById("pills-home")
            let form2 = document.getElementById("pills-profile")
            form.classList.remove("active")
            form.classList.remove("show")
            form2.classList.add("active","show")
        },

         cambiarForm2(){
            let form2 = document.getElementById("pills-home")
            let form = document.getElementById("pills-profile")
            form.classList.remove("active")
            form.classList.remove("show")
            form2.classList.add("active","show")
        },
        
        registrarUsuario(){
            axios.post('/api/clients',"firstName="+ this.primerNombre +"&lastName="+ this.apellido +"&email="+ this.emailRegistro +"&password="+ this.contraseñaRegistro)
            .then(() => {
                this.email = this.emailRegistro
                this.contraseña = this.contraseñaRegistro
                this.ingresarUsuario()
                
          })
.catch((error) =>{this.error = error.response.data
 

    if (this.error == "Missing firstName"){
        Swal.fire({
            confirmButtonColor: "#1bb5db",
            background:'#212121',
            color:'white',
            icon: 'error',
            // title: 'Oops...Error 403',
            text: 'Debes completar el campo : Nombre',
            
          })
    } if (this.error == "Missing lastName"){
        Swal.fire({
            confirmButtonColor: "#1bb5db",
            background:'#212121',
            color:'white',
            icon: 'error',
            // title: 'Oops...Error 403',
            text: 'Debes completar el campo: Apellido',
           
          })

    }
    if (this.error == "Missing email"){
        Swal.fire({
            confirmButtonColor: "#1bb5db",
            background:'#212121',
            color:'white',
            icon: 'error',
            // title: 'Oops...Error 403',
            text: 'Debes completar el campor: Email',
           
          })

    }
    if (this.error == "Missing password"){
        Swal.fire({
            confirmButtonColor: "#1bb5db",
            background:'#212121',
            color:'white',
            icon: 'error',
            // title: 'Oops...Error 403',
            text: 'Debes completar el campo: Contraseña',
           
          })

    }
    if (this.error == "Email already in use"){
        Swal.fire({
            confirmButtonColor: "#1bb5db",
            background:'#212121',
            color:'white',
            icon: 'error',
            // title: 'Oops...Error 403',
            text: 'Ya existe otro usuario con este email',
           
          })

    }
}
)
        }


     
   
},
    computed: {
        
       
    },

}).mount('#app')