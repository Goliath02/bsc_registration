<script setup lang="ts">

import BscToolbar from "@/AdminPannel/components/BscToolbar.vue";
</script>

<template>
  <main class="flex h-screen max-h-screen md:flex-row flex-col">
    <BscToolbar/>
	  <div class="flex-1" >
		  <router-view></router-view>
	  </div>
  </main>
</template>

<style scoped>

</style>