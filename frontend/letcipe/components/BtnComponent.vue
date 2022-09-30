<template>
  <div id="app">
    <v-container fluid>
      <v-speed-dial
        v-model="fab"
        class="dial-btn"
        bottom
        right
        :direction="direction"
        :open-on-hover="hover"
        :transition="transition"
      >
        <template #activator>
          <v-btn v-model="fab" color="blue darken-2" dark fab>
            <v-icon v-if="fab">mdi-close</v-icon>
            <v-icon v-else>mdi-account-circle</v-icon>
          </v-btn>
        </template>
        <v-btn fab dark small color="green">
          <v-icon>mdi-pencil</v-icon>
        </v-btn>
        <v-btn fab dark small color="indigo">
          <v-icon>mdi-plus</v-icon>
        </v-btn>
        <v-btn fab dark small color="red">
          <v-icon>mdi-delete</v-icon>
        </v-btn>
      </v-speed-dial>
    </v-container>
  </div>
</template>

<script>
export default {
  name: 'ButtonComponent',
}
</script>

<style scoped>
#create .v-speed-dial {
  position: absolute;
  margin-right: 3%;
}

#create .v-btn--floating {
  position: relative;
  margin-right: 3%;
}
</style> 