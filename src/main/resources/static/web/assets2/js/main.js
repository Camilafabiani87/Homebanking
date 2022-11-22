const app = Vue.createApp({
    data() {
        return {
          url:"/api/clients/current",
      
          clients: [],
          accounts :[],
          loans:[],
          primerNombre : "",
          id : "",
          number:"",
          creationDate:"",
          balance:"",
          numeroRandom:"",
          balanceCuentaNueva:"",
          fechaCuentaNueva:"",
          error: "",
          tipoCuenta:"",

        }
    },

    created() {
        this.loadData(this.url)
    },
    mounted() {
       
    },
    methods: {
        loadData(url) {
            axios.get(url)
            .then(respuesta => {
                this.clients = respuesta.data
               //console.log(respuesta)
               // OTRA OPCION this.accounts = respuesta.data.accounts
                this.accounts = this.clients.accounts.sort((a, b)=> 
                { if (a.id < b.id)
                    {return -1} 
                    if (a.id > b.id){ return 1}
                
                })
                //console.log(this.accounts);
               this.loans = this.clients.loans.sort((a, b)=> 
               { if (a.id < b.id)
                   {return -1} 
                   if (a.id > b.id){ return 1}
               
               })
               //console.log(this.loans);
              //  if(this.accounts.length == 0) {
              //   this.crearCuenta()
              //  } 

            }

               

            
            )
            .catch(error => console.error(error.message));
        },

        cerrarSesion(){
            
            const swalWithBootstrapButtons = Swal.mixin({
                customClass: {
                  confirmButton: 'btn btn-success',
                  cancelButton: 'btn btn-danger'
                  
                },
                buttonsStyling: false
              })
              
              swalWithBootstrapButtons.fire({
                background:'#212121',
                color:'white',
                title: '¿Seguro deseas cerrar sesión?',
                // icon: 'warning',
                showCancelButton: true,
                // confirmButtonColor: '#d14334',
                // cancelButtonColor: '#d33',
                confirmButtonText: 'Si', 
                cancelButtonText: 'No',
                

                reverseButtons: false
              }).then((result) => {
              if (result.isConfirmed) {
                    Swal.fire({
                      background:'#212121',
                        color:'white',
                        position: 'top-center',
                        // icon: 'success',
                        title: 'Su sesión ha sido finalizada',
                        showConfirmButton: false,
                        timer: 10500
                      })
                    axios.post('/api/logout').then(response =>  window.location.href = '/web/index.html')
                    
                  
                }
              })
            
            
        },
        crearCuenta(){
          Swal.fire({
            background:'#212121',
            color:'white',
            title: '¿Que tipo de cuenta desea?',
            // icon: 'warning',
            showDenyButton: true,
            showCancelButton: true,
            confirmButtonColor: '#1bb5db',
            denyButtonColor: '#1bb5db',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Cuenta Corriente',
            denyButtonText: 'Cuenta de ahorro'
          })
          .then((result) => {
            if (result.isConfirmed) {
              
            
          axios.post('/api/clients/current/accounts',"accountType=CORRIENTE")
               
             
            } else if(result.isDenied){
              axios.post('/api/clients/current/accounts',"accountType=AHORRO")
            }
            
          }) 
          .then(() => {
              this.numero = this.numeroRandom
              this.creationDate = this.fechaCuentaNueva
              window.location.reload()
            
        })
.catch((error) =>{this.error = error.response.data
//   if (this.error == "Su cuenta se ha creado exitosamente"){

//   } else {
   
//       Swal.fire({
//         icon: 'error',
//         title: 'Oops...Error 403',
//         text: 'No puede tener mas de 3 cuentas asociadas',
        
//       })
// }
}
)
      },
      eliminarCuenta(cuenta){
        Swal.fire({
          background: "#212121",
          color: "white",
          title: "¿Seguro desea borrar la cuenta?",
          // icon: 'warning',
          showCancelButton: true,
          confirmButtonColor: "#1bb5db",
          cancelButtonColor: "#d33",
          confirmButtonText: "Si, confirmo ",
          cancelButtonText: "Cancelar",
        }) .then((result)=> {
          if (result.isConfirmed) {
            axios.patch('/api/clients/current/accounts', 'id=' + cuenta.id)
            .then(() => {
              Swal.fire({
                background: "#212121",
                confirmButtonColor: "#1bb5db",
                 title: 'Su cuenta ha sido borrada'})
              .then(()=> this.loadData(this.url) )
          
          
            }
            )
          }
  
           }) .catch((error) => {
            this.error = error.response.data;
            console.log(error);
            if (this.error == "La cuenta tiene que estar vacia para borrarla") {
              Swal.fire({
                confirmButtonColor: "#1bb5db",
                background: "#212121",
                color: "white",
                icon: "error",
                // title: "Error",
                text: "No puede borrar esta cuenta ya que posee saldo en ella",
              })
            }
      });
      
  
    },    
    },
  
    computed: {
       

    },

}).mount('#app')

