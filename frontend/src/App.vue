<script setup>
import { useRegistrationStore } from "@/stores/RegistrationStore.js";
import ResponseModal from "@/components/ResponseModal.vue";

const store = useRegistrationStore();
</script>

<template>
  <div class="bg-black h-screen flex flex-col">
    <router-view :key="$route.fullPath" v-slot="{ Component }">
      <transition name="fade" mode="out-in">
        <component :is="Component" />
      </transition>
    </router-view>
  </div>

  <ResponseModal v-if="useRegistrationStore().requestFailed" />
</template>

<style scoped>
body {
  background-color: #000000;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
