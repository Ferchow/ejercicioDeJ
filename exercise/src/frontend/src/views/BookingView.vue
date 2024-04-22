<template>
    <v-data-table
      :headers="headers"
      :items="reservaciones"
      sort-by="id"
      class="elevation-1"
    >
      <template v-slot:top>
        <v-toolbar
          flat
        >
          <v-toolbar-title>Reservaciones</v-toolbar-title>
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
            max-width="1000px"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                color="primary"
                dark
                class="mb-2"
                v-bind="attrs"
                v-on="on"
              >
                Nueva Reservación
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
                        v-model="editedItem.idRoom"
                        label="# de habitación"
                      ></v-text-field>
                    </v-col>
                    <v-col
                      cols="12"
                      sm="6"
                      md="4"
                    >
                      <v-text-field
                        v-model="editedItem.idcustomer"
                        label="Huesped #"
                      ></v-text-field>
                    </v-col>
                    <v-col
                      cols="12"
                      sm="6"
                      md="4"
                    >
                      <v-text-field
                        v-model="editedItem.status"
                        label="estatus"
                      ></v-text-field>
                    </v-col>
                    <v-col
                      cols="12"
                      sm="6"
                      md="4"
                    >
                      <v-text-field
                        v-model="editedItem.resPrice"
                        label="precio de reserva"
                      ></v-text-field>
                    </v-col>
                    <!-- <v-col
                      cols="12"
                      sm="6"
                      md="15"
                    >
                   <v-text-field
                        v-model="editedItem.dias"
                        label="dias de reserva"
                        prepend-icon="mdi-calendar"
                        >
                    </v-text-field>
                    <v-date-picker
                        v-model="dates"
                        range
                        >
                    </v-date-picker>
                    model: {{ dates }}
                    </v-col>
                    <v-col>
                        <v-text-field
                            v-model="dateRangeText"
                            label="Date range"
                            prepend-icon="mdi-calendar"
                            readonly
                        ></v-text-field>
                    </v-col> -->
                    <v-menu
                        ref="menu"
                        v-model="menu"
                        :close-on-content-click="false"
                        transition="scale-transition"
                        offset-y
                        min-width="auto"
                        >
                        <template v-slot:activator="{ on, attrs }">
                            <v-text-field
                            v-model="dateRangeText"
                            label="Dias"
                            prepend-icon="mdi-calendar"
                            readonly
                            v-bind="attrs"
                            v-on="on"
                            ></v-text-field>
                        </template>
                        <v-date-picker
                            v-model="dates"
                            :active-picker.sync="activePicker"
                            range
                            :max="(new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substring(0, 10)"
                            min="1950-01-01"
                            @change="saveDate"
                        ></v-date-picker>
                        </v-menu>
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
  import usersData from '../../../../bookings.json'
  import savefiles from '../components/saveFiles'
  export default {
    name: 'CuartosView',
    data: () => ({
      dialog: false,
      headers: [
        { text: 'Folio', align: 'left', sortable: false, value: 'id'},
        { text: '# de Cuarto', value: 'idRoom' },
        { text: 'Precio Final', value: 'resPrice' },
        { text: 'Estatus', value: 'status' },
        { text: 'Cliente', value: 'idcustomer'},
        { text: 'Días', value: 'days.length'},
        { text: 'Actions', value: 'actions', sortable: false}
      ],
      reservaciones: [],
      bookings: [],
      dates:['2023-09-10', '2023-09-20'],
      msh:'this shit is not working',
      editedIndex: -1,
      editedItem: {
        id:'',
        idRoom:'',
        resPrice:'',
        status:'',
        idcustomer:[],
        days:''
      },
      defaultItem: {
        id:'',
        idRoom:'',
        resPrice:'',
        status:'',
        idcustomer:[],
        days:''
      }
    }
  ),
  
    computed: {
      formTitle () {
        return this.editedIndex === -1 ? 'Nueva Reservación' : 'Editar Reservación'
      },
      dateRangeText () {
        return this.dates.join(' ~ ')
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
        this.bookings =
         [
          {
            id:1,
            idRoom:2,
            resPrice:1999.96,
            status:'ongoing',
            idcustomer:[
                35
            ],
            days:[
                1,2,3,4
            ]
          },
          {
            id:2,
            idRoom:3,
            resPrice:1500,
            status:'pending',
            idcustomer:[
                12
            ],
            days:[
                1
            ]
          }
        ]
      },
  
      retrieveClientes(){
        this.reservaciones = usersData.map(this.getDisplayclientes);
        console.log(usersData.data)
      },
  
      getDisplayclientes(reservaciones) {
        return {
          id: reservaciones.id,
          idRoom: reservaciones.idRoom,
          resPrice: reservaciones.resPrice,
          status: reservaciones.status,
          idcustomer: reservaciones.idcustomer,
          days: reservaciones.days
        };
      },
  
      editItem (item) {
        //this.dates = Object.assign({},item.days)
        this.editedIndex = this.reservaciones.indexOf(item)
        this.editedItem = Object.assign({}, item)
        this.dialog = true
      },
  
      deleteItem (item) {
        const index = this.reservaciones.indexOf(item)
        confirm('Are you sure you want to delete this item?') && this.reservaciones.splice(index, 1)
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
          Object.assign(this.reservaciones[this.editedIndex], this.editedItem)
        } else {
          this.reservaciones.push(this.editedItem)
        }
        this.close()
      },
      saveClients(){
        savefiles.saveData(this.reservaciones)
      },
      saveDate (date) {
        this.$refs.menu.save(date)
      },
      
    },
    mounted(){
      this.retrieveClientes()
    }
  }
  </script>