<script setup>

import Header from "@/components/BSC_Header.vue";
</script>

<template>
  <div class="bg-black h-screen flex flex-col ">
    <Header></Header>

    <router-view v-slot="{ Component }">
      <transition name="fade" mode="out-in">
        <component :is="Component"/>
      </transition>
    </router-view>

  </div>

</template>

<style scoped>
body {
  background-color: #000000;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
