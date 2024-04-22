<template>
  <v-data-table
    :headers="headers"
    :items="habitaciones"
    sort-by="name"
    class="elevation-1"
  >
    <template v-slot:top>
      <v-toolbar
        flat
      >
        <v-toolbar-title>Rooms</v-toolbar-title>
        <v-divider
          class="mx-4"
          inset
          vertical
        ></v-divider>
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
              dark
              class="mb-2"
              v-bind="attrs"
              v-on="on"
            >
              Nuevo Cuarto
            </v-btn>
          </template>
          <v-card>
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
                      v-model="editedItem.name"
                      label="Nombre"
                    ></v-text-field>
                  </v-col>
                  <v-col
                    cols="12"
                    sm="6"
                    md="4"
                  >
                    <v-text-field
                      v-model="editedItem.beds"
                      label="# camas"
                    ></v-text-field>
                  </v-col>
                  <v-col
                    cols="12"
                    sm="6"
                    md="4"
                  >
                    <v-text-field
                      v-model="editedItem.size"
                      label="# de huespedes"
                    ></v-text-field>
                  </v-col>
                  <v-col
                    cols="12"
                    sm="6"
                    md="4"
                  >
                    <v-text-field
                      v-model="editedItem.available"
                      label="disponibilidad"
                    ></v-text-field>
                  </v-col>
                  <v-col
                    cols="12"
                    sm="6"
                    md="4"
                  >
                    <v-text-field
                      v-model="editedItem.price"
                      label="precio"
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
import usersData from '../../../../rooms.json'
import savefiles from '../components/saveFiles'
export default {
  name: 'CuartosView',
  data: () => ({
    dialog: false,
    headers: [
      { text: 'Nombre', align: 'left', sortable: false, value: 'name'},
      { text: 'camas', value: 'beds' },
      { text: 'tamaño', value: 'size' },
      { text: 'Disponibilidad', value: 'available' },
      { text: 'Precio', value: 'price'},
      { text: 'Actions', value: 'actions', sortable: false}
    ],
    habitaciones: [],
    cuartos: [],
    msh:'this shit is not working',
    editedIndex: -1,
    editedItem: {
       size: '',
       price:'',
       name:'',
       available:'',
       beds:''
    },
    defaultItem: {
      size: '',
      price:'',
      name:'',
      available:'',
      beds:''
    }
  }
),

  computed: {
    formTitle () {
      return this.editedIndex === -1 ? 'Nueva Habitación' : 'Editar Habitación'
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
      this.cuartos =
       [
        {
          size: 2,
          price:'500',
          name:'Standard Room',
          available:false,
          beds:'1'
        },
        {
          size: 2,
          price:'500',
          name:'Standard Room',
          available: true,
          beds:'1'
        }
      ]
    },

    retrieveClientes(){
      this.habitaciones = usersData.map(this.getDisplayclientes);
      console.log(usersData.data)
    },

    getDisplayclientes(habitaciones) {
      return {
        size: habitaciones.size,
        price: habitaciones.price,
        name: habitaciones.name,
        available: habitaciones.available,
        beds: habitaciones.beds,
      };
    },

    editItem (item) {
      this.editedIndex = this.habitaciones.indexOf(item)
      this.editedItem = Object.assign({}, item)
      this.dialog = true
    },

    deleteItem (item) {
      const index = this.habitaciones.indexOf(item)
      confirm('Are you sure you want to delete this item?') && this.habitaciones.splice(index, 1)
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
        Object.assign(this.habitaciones[this.editedIndex], this.editedItem)
      } else {
        this.habitaciones.push(this.editedItem)
      }
      this.close()
    },
    saveClients(){
      savefiles.saveData(this.habitaciones)
    }
    
  },
  mounted(){
    this.retrieveClientes()
  }
}
</script>