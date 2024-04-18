<template>
  <v-app>
    <v-app-bar
      app
      color="primary"
      dark
    >
      <div class="d-flex align-center">
        <h2>Clientes</h2>
      </div>

      <v-spacer></v-spacer>

      <v-btn
        to= rooms
        target="_blank"
        text
      >
        <span class="mr-2">Menu</span>
        <v-icon>mdi-open-in-new</v-icon>
      </v-btn>
    </v-app-bar>

    <v-main>
      <HelloWorld/>
    </v-main>
  </v-app>
</template>

<script>
import HelloWorld from './components/HelloWorld.vue';
import rooms from './componets/Rooms.vue';

export default {
  name: 'App',

  components: {
    HelloWorld,
    rooms
  },

  /* data: () => ({
    //
  }), */
};
</script>
