<template>
  <!--v-container>
    <v-row class="text-center">
      <v-col cols="12">
        
      </v-col>

      <v-col class="mb-4">
        <h1 class="display-2 font-weight-bold mb-3">
          Welcome to Vuetify
        </h1>

        <p class="subheading font-weight-regular">
          For help and collaboration with other Vuetify developers,
          <br>please join our online
          <a
            href="https://community.vuetifyjs.com"
            target="_blank"
          >Discord Community</a>
        </p>
      </v-col>

      <v-col
        class="mb-5"
        cols="12"
      >
        <h2 class="headline font-weight-bold mb-3">
          What's next?
        </h2>

        <v-row justify="center">
          <a
            v-for="(next, i) in whatsNext"
            :key="i"
            :href="next.href"
            class="subheading mx-3"
            target="_blank"
          >
            {{ next.text }}
          </a>
        </v-row>
      </v-col>

      <v-col
        class="mb-5"
        cols="12"
      >
        <h2 class="headline font-weight-bold mb-3">
          Important Links
        </h2>

        <v-row justify="center">
          <a
            v-for="(link, i) in importantLinks"
            :key="i"
            :href="link.href"
            class="subheading mx-3"
            target="_blank"
          >
            {{ link.text }}
          </a>
        </v-row>
      </v-col>

      <v-col
        class="mb-5"
        cols="12"
      >
        <h2 class="headline font-weight-bold mb-3">
          Ecosystem
        </h2>

        <v-row justify="center">
          <a
            v-for="(eco, i) in ecosystem"
            :key="i"
            :href="eco.href"
            class="subheading mx-3"
            target="_blank"
          >
            {{ eco.text }}
          </a>
        </v-row>
      </v-col>
    </v-row> -->
  <div>
    <v-toolbar flat color="white">
      <v-toolbar-title>Clientes</v-toolbar-title>
      <v-divider
        class="mx-2"
        inset
        vertical
      ></v-divider>
      <v-spacer></v-spacer>
      <v-dialog v-model="dialog" max-width="500px">
        <template v-slot:activator="{ on }">
          <v-btn color="primary" dark class="mb-2" v-on="on">Nuevo Cliente</v-btn>
        </template>
        <v-card>
          <v-card-title>
            <span class="headline">{{ formTitle }}</span>
          </v-card-title>

          <v-card-text>
            <v-container grid-list-md>
              <v-layout wrap>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedItem.first" label="Nombre"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedItem.lastname" label="Apellido"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedItem.email" label="email"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedItem.country" label="País"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedItem.company" label="Empresa"></v-text-field>
                </v-flex>
              </v-layout>
            </v-container>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" flat @click="close">Cancelar</v-btn>
            <v-btn color="blue darken-1" flat @click="save">Guardar</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-toolbar>
    <v-data-table
      :headers="headers"
      :items="clientes"
      hide-actions
      class="elevation-1"
    >
      <template v-slot:items="props">
        <td>{{ props.item.first }}</td>
        <td class="text-xs-right">{{ props.item.lastname }}</td>
        <td class="text-xs-right">{{ props.item.email }}</td>
        <td class="text-xs-right">{{ props.item.country }}</td>
        <td class="text-xs-right">{{ props.item.company }}</td>
        <td class="justify-center layout px-0">
          <v-icon
            small
            class="mr-2"
            color="success"
            @click="editItem(props.item)"
          >
            Editar
          </v-icon>
          <v-icon
            small
            color="error"
            @click="deleteItem(props.item)"
          >
            Eliminar
          </v-icon>
        </td>
      </template>
      <template v-slot:no-data>
        <v-btn color="primary" @click="initialize">Reset</v-btn>
      </template>
    </v-data-table>
    <h3>{{msh}}</h3>
  </div>
</template>

<script>
  export default {
    name: 'HelloWorld',
    data: () => ({
      dialog: false,
      headers: [
        { text: 'Nombre', align: 'left', sortable: false, value: 'first'},
        { text: 'Apellido', value: 'last' },
        { text: 'email', value: 'email' },
        { text: 'País', value: 'country' },
        { text: 'Empresa', value: 'company'},
        { text: 'Actions', value: 'first', sortable: false}
      ],
      clientes: [],
      msh:'this shit is not working',
      editedIndex: -1,
      editedItem: {
        first: '',
        lastname: '',
        email: '',
        country: '',
        company: ''
      },
      defaultItem: {
        first: '',
        lastname: '',
        email: '',
        country: '',
        company: ''
      }
    }
  ),
    mounted () {
      fetch("~/api/messages/Customers")
      .then((response) => response.text())
      .then((data) => {
        this.msh = data;        
      })
      //console.log(this.msh)
    },
    computed: {
      formTitle () {
        return this.editedIndex === -1 ? 'Nuevo Cliente' : 'Editar Cliente'
      }
    },

    watch: {
      dialog (val) {
        val || this.close()
      }
    },

    created () {
      this.initialize()
    },

    methods: {
      initialize () {
        this.clientes = this.msh
        /* [
          {
            country:"Switzerland",
            last:"Veum",
            created_at:"2014-12-25T04:06:27.981Z",
            company:"Hilll, Mayert and Wolf",
            id:1,
            email:"isidro_von@hotmail.com",
            first:"Torrey"
          },
          {
            country:"Switzerland",
            last:"Veum",
            created_at:"2014-12-25T04:06:27.981Z",
            company:"Hilll, Mayert and Wolf",
            id:1,
            email:"isidro_von@hotmail.com",
            first:"Torrey"
          }
        ] */
      },

      editItem (item) {
        this.editedIndex = this.clientes.indexOf(item)
        this.editedItem = Object.assign({}, item)
        this.dialog = true
      },

      deleteItem (item) {
        const index = this.clientes.indexOf(item)
        confirm('Are you sure you want to delete this item?') && this.clientes.splice(index, 1)
      },

      close () {
        this.dialog = false
        setTimeout(() => {
          this.editedItem = Object.assign({}, this.defaultItem)
          this.editedIndex = -1
        }, 300)
      },

      save () {
        if (this.editedIndex > -1) {
          Object.assign(this.clientes[this.editedIndex], this.editedItem)
        } else {
          this.clientes.push(this.editedItem)
        }
        this.close()
      }
    }
  }
</script>
