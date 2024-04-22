<template>
  <v-app class="grey lighten-4">
    <NavVar/>
    <v-main>
      <router-view/>
    </v-main>
  </v-app>
</template>

<script>
import NavVar from '@/components/NavVar.vue';

export default {
  name: 'App',
  components: {
    NavVar
  },
  data: () => ({
    //
  }),
};
</script>
