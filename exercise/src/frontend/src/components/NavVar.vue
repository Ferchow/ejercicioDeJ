<template>
    <nav>
        <v-toolbar flat app class="grey--text">
            <v-toolbar-side-icon @click="drawer = !drawer">
                <v-icon>mdi-menu</v-icon>
            </v-toolbar-side-icon>
            <v-toolbar-title>
                <span class="font-weight-light">
                    Menu
                </span>
            </v-toolbar-title>
        </v-toolbar>
        <v-navigation-drawer app v-model="drawer" class="primary" temporary>
            <v-list>
                <v-list-tile>
                    <v-list-tile-content>
                        <v-list-tile-title class="text-h6 white--text">
                            Menu
                        </v-list-tile-title>
                    </v-list-tile-content>
                </v-list-tile>
                <v-list-item v-for="link in links" :key="link.text" router :to="link.route">
                    <v-list-item-action>
                        <v-icon class="white--text">{{link.icon}}</v-icon>
                    </v-list-item-action>
                    <v-list-item-content>
                        <v-list-item-title class="white--text">{{link.text}}</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
            </v-list>
        </v-navigation-drawer>
    </nav>
</template>

<script>
export default{
    name: 'NavVar',
    data () {
        return{
            drawer: false,
            links: [
                { icon: 'mdi-account-group',  text: 'Clients', route: '/clientes'},
                { icon: 'mdi-bed',  text: 'Habitaciones', route: '/Rooms'},
                { icon: 'mdi-calendar-multiselect',  text: 'Reservar', route: '/reservaciones'}
            ]
        }
    },
    methods(){

    }
}
</script>