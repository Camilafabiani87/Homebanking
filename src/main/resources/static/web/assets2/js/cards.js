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
          fecha: new Date(),
          fechaActual :"",
          fechaVencimiento:"",
          dateFalse: new Date("2026-11-20")
          
          
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
                 //console.log(this.cards);
             this.cardsDebito = this.clients.cards.filter(card => card.type == 'DEBITO' ) 
             this.cardsCredito = this.clients.cards.filter(card => card.type == 'CREDITO' ) 

             this.fechaActual = this.fecha.getFullYear()+ "-" + (this.fecha.getMonth() +1) + "-" + (this.fecha.getDate())
             console.log(this.fechaActual);
            // this.fechaVencimiento = new Date("2005-12-30"); 
            
            //  this.fechaVencimiento =  this.clients.cards.map(card => card.thruDate)
         
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
      eliminarTarjeta(tarjeta){
        Swal.fire({
          background: "#212121",
          color: "white",
          title: "¿Seguro desea borrar tarjeta?",
          // icon: 'warning',
          showCancelButton: true,
          confirmButtonColor: "#1bb5db",
          cancelButtonColor: "#d33",
          confirmButtonText: "Si, confirmo ",
          cancelButtonText: "Cancelar",
        }) .then((result)=> {
          if (result.isConfirmed) {
            axios.patch('/api/client/current/cards', 'id=' + tarjeta.id)
            .then(() => {
              Swal.fire({
                background: "#212121",
                confirmButtonColor: "#1bb5db",
                 title: 'Su tarjeta ha sido borrada'})
              .then(()=> this.loadData(this.url) )
          
          
            }
            )
          }
 
           })

    },
//     validarFechaVencimiento(date){
//       var x =new Date();
//       var fecha = date.split("/");
//       x.setFullYear(fecha[2],fecha[1]-1,fecha[0]);
//       var today = new Date();
 
//       if (x >= today)
//         return false;
//       else
//         return true;
// }
    // mostrarVencimiento(){
    //   let fechaActual = new Date();
    //   let fechaVencimiento = this.clients.cards.thruDate
    //   console.log(fechaVencimiento);
    //   if(fechaVencimiento > fechaActual){
    //       "VENCIDA"
    //   }
    
    // }
       
    },
    
    computed: {
        

    },

}).mount('#app')