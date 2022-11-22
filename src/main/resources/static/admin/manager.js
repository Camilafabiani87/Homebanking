const app = Vue.createApp({
    data() {
        return {
            url: "http://localhost:8080/rest/clients",
            arrayJson: [],
            clients: [],
            primerNombre : "",
            apellido : "",
            email : "",
            id : "",
            

           
        }
    },

    mounted(){
        titulo = document.getElementsByClassName("contenedorTitulo")
    },

    created() {
        this.loadData(this.url)
    },
   
    methods: {
        loadData(url) {
            axios.get(url)
            .then(response =>{ this.arrayJson = response 
                this.clients = this.arrayJson.data._embedded.clients

                

            }
                
                   
            )
            .catch(error => console.error(error.message));
        },
        
        agregarCliente(){
            clienteAgregado = {
                primerNombre : this.primerNombre,
                apellido : this.apellido,
                email : this.email,

                
            }
            this.post(clienteAgregado)
            
        }, 
        post(clienteNuevo){  
                   axios.post(this.url, clienteNuevo).then(()=> {
                    this.primerNombre = ""
                    this.apellido = ""
                    this.email = ""
                    this.loadData(this.url)})     
                    .catch(error => console.error(error.message));
                },
        eliminarCliente(cliente){
            axios.delete(cliente._links.self.href).then(()=> {
                this.loadData(this.url)})
        },
       
          editarCliente(){
             axios.put(this.id, {
                primerNombre:this.primerNombre,
                apellido:this.apellido,
                email:this.email
            }).then(()=> {
                this.primerNombre = ""
                    this.apellido = ""
                    this.email = ""
                this.loadData(this.url)})
         },
        mostrarDatos(cliente){
            this.id = cliente._links.self.href
            this.primerNombre = cliente.primerNombre
            this.apellido = cliente.apellido
            this.email = cliente.email

        },
   
},
    computed: {
        
       
    },

}).mount('#app')