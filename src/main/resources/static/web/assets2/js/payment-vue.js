const app = Vue.createApp({
    data() {
        return {
          url:"/api/clients/current",
          clients: [],
          cards: [],
          primerNombre : "",
          number:"",
          creationDate:"",
          balance:"",
          cardsDebito:[],
          cardsCredito:[],
          numeroTarjeta:"",
          tipoTarjeta:"",
          servicio:"",
          expiracion:"",
          cvv:"",
          monto:"",
          descripcion:"",
        
          
          
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
                this.cards = this.clients.cards.sort((a, b)=> 
                { if (a.id < b.id)
                    {return -1} 
                    if (a.id > b.id){ return 1}
                
                })
             
         
            }
              )
   
            .catch(error => console.error(error.message));
        },
        pagarServicio(){
            axios.post("/api/clients/current/cardTransaction" , {
                cardNumber: this.numeroTarjeta,
                cvv: this.cvv,
                amount: this.monto,
                description: this.descripcion,
              
            }).then(() => window.location.href='/web/accounts.html')
            .catch((error) => {
              this.error = error.response.data;
              console.log(error);
           
              if (this.error == "La tarjeta no pertenece al cliente") {
                Swal.fire({
                  confirmButtonColor: "#1bb5db",
                  background: "#212121",
                  color: "white",
                  icon: "error",
                  // title: "Error",
                  text: "La tarjeta no pertenece al cliente",
                });
              }
              if (this.error == "El monto supera el balance") {
                Swal.fire({
                  confirmButtonColor: "#1bb5db",
                  background: "#212121",
                  color: "white",
                  icon: "error",
                  // title: "Error",
                  text: "El monto supera el balance",
                });
              }
              if (this.error == "Codigo de seguridad invalido") {
                Swal.fire({
                  confirmButtonColor: "#1bb5db",
                  background: "#212121",
                  color: "white",
                  icon: "error",
                  // title: "Error",
                  text: "Codigo de seguridad invalido",
                });
              }
              if (this.error == "Tarjeta expirada") {
                Swal.fire({
                  confirmButtonColor: "#1bb5db",
                  background: "#212121",
                  color: "white",
                  icon: "error",
                  // title: "Error",
                  text: "Tarjeta expirada",
                });
              }
        })
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
  

       
    },
    
    computed: {
        

    },

}).mount('#app')