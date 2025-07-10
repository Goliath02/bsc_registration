<script setup lang="ts">

import BscToolbar from "@/AdminPannel/components/BscToolbar.vue";
</script>

<template>
  <main class="flex h-screen">
    <BscToolbar/>
	  <div class="flex-1 flex justify-center items-center" >
		  <router-view></router-view>

	  </div>
  </main>
</template>

<style scoped>

</style>