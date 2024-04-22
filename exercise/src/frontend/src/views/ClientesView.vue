<template>
  <v-data-table
    :headers="headers"
    :items="usuarios"
    sort-by="name"
    class="elevation-1"
  >
    <v-skeleton-loader
      class="mx-auto"
      type="list-item-avatar"
      >
    </v-skeleton-loader>
    <template v-slot:top>
      <v-toolbar
        flat
        :color="`grey ${theme.isDark ? 'darken-2' : 'lighten-4'}`"
        class="pa-3"
      >
        <v-toolbar-title>Clients</v-toolbar-title>
        <v-divider
          class="mx-4"
          inset
          vertical
        ></v-divider>
        <v-spacer></v-spacer>
        <v-spacer></v-spacer>
        <v-btn
          color="success"
          dark
          @click="saveClients">
          Guardar cambios
        </v-btn>
        <v-spacer></v-spacer>
        <v-dialog
          v-model="dialog"
          max-width="500px"
        >
          <template v-slot:activator="{ on, attrs }">
            <v-btn
              color="primary"
              flat
              class="mb-2"
              v-bind="attrs"
              v-on="on"
            >
              Nuevo Cliente
            </v-btn>
          </template>
          <v-card>
            <v-skeleton-loader
              class="mx-auto"
              max-width="300"
              type="card"
              >
            </v-skeleton-loader>
            <v-card-title>
              <span class="text-h5">{{ formTitle }}</span>
            </v-card-title>

            <v-card-text>
              <v-container>
                <v-row>
                  <v-col
                    cols="12"
                    sm="6"
                    md="4"
                  >
                    <v-text-field
                      v-model="editedItem.first"
                      label="Name"
                    ></v-text-field>
                  </v-col>
                  <v-col
                    cols="12"
                    sm="6"
                    md="4"
                  >
                    <v-text-field
                      v-model="editedItem.last"
                      label="Lastname"
                    ></v-text-field>
                  </v-col>
                  <v-col
                    cols="12"
                    sm="6"
                    md="4"
                  >
                    <v-text-field
                      v-model="editedItem.email"
                      label="email"
                    ></v-text-field>
                  </v-col>
                  <v-col
                    cols="12"
                    sm="6"
                    md="4"
                  >
                    <v-text-field
                      v-model="editedItem.country"
                      label="Country"
                    ></v-text-field>
                  </v-col>
                  <v-col
                    cols="12"
                    sm="6"
                    md="4"
                  >
                    <v-text-field
                      v-model="editedItem.company"
                      label="Company"
                    ></v-text-field>
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn
                color="blue darken-1"
                text
                @click="close"
              >
                Cancel
              </v-btn>
              <v-btn
                color="blue darken-1"
                text
                @click="save"
              >
                Save
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <v-dialog v-model="dialogDelete" max-width="500px">
          <v-card>
            <v-card-title class="text-h5">Are you sure you want to delete this item?</v-card-title>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="closeDelete">Cancel</v-btn>
              <v-btn color="blue darken-1" text @click="deleteItemConfirm">OK</v-btn>
              <v-spacer></v-spacer>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-toolbar>
    </template>
    <template v-slot:[`item.actions`]="{ item }">
      <v-icon
        small
        class="mr-2"
        @click="editItem(item)"
      >
        mdi-pencil
      </v-icon>
      <v-icon
        small
        @click="deleteItem(item)"
      >mdi-delete</v-icon>
    </template>
    <template v-slot:no-data>
      <v-btn
        color="primary"
        @click="initialize"
      >
        Reset
      </v-btn>
    </template>
  </v-data-table>
</template>

<script>
import usersData from '../../../../customers.json'
import savefiles from '../components/saveFiles'
export default {
  name: 'ClientesView',
  inject: {
    theme: {
      default: { isDark: false },
    },
  },
  data: () => ({
    dialog: false,
    headers: [
      { text: 'Nombre', align: 'left', sortable: false, value: 'first'},
      { text: 'Apellido', value: 'last' },
      { text: 'email', value: 'email' },
      { text: 'País', value: 'country' },
      { text: 'Empresa', value: 'company'},
      { text: 'Actions', value: 'actions', sortable: false}
    ],
    clientes: [],
    usuarios: [],
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
      this.clientes =
       [
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
      ]
    },

    retrieveClientes(){
      this.usuarios = usersData.map(this.getDisplayclientes);
      console.log(usersData.data)
    },

    getDisplayclientes(clientes) {
      return {
        country: clientes.country,
        company: clientes.company,
        id: clientes.id,
        first: clientes.first,
        last: clientes.last,
        email: clientes.email,
      };
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
    },
    saveClients(){
      savefiles.saveData(this.clientes)
    }
    
  },
  mounted(){
    this.retrieveClientes()
  }
}
</script>