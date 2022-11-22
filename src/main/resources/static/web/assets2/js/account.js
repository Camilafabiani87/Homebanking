const app = Vue.createApp({
    data() {
        return {
          url:"/api/clients/accounts/",
      
          clients: [],
          accounts :[],
          transactions:[],
          primerNombre : "",
          id: new URLSearchParams(location.search).get('id'),
          number:"",
          creationDate:"",
          balance:"",
          cuenta1: "",
          cuenta2:"",
          elegir:"",
          numeroCuenta:"",
         

          
        }
    },

    created() {
        this.loadData(this.url + this.id)
    },
    mounted() {
       
    },
    methods: {
        loadData(url) {
            axios.get(url)
            .then(respuesta => {
                this.accounts = respuesta.data
                this.numeroCuenta = this.accounts.number
                // console.log(respuesta);
                console.log(this.accounts);
                //console.log(respuesta)
                this.transactions = this.accounts.transactions
                //console.log(this.accounts);
                this.transactions = this.accounts.transactions.sort((a, b)=> 
                { if (a.creationDate > b.creationDate)
                    {return -1} 
                    if (a.creationDate < b.creationDate){ return 1}
                
                })
                // console.log(this.transactions);
                
          
             

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
        descargarPdf(){
          console.log(this.numeroCuenta);
          axios.post('/api/pdf', "number=" + this.numeroCuenta + "&localDateMinus='semana'")
          .then(()=> window.location.href='/api/pdf' 
          
          )
    
        },   
    },
   
    computed: {
     

    },

}).mount('#app')