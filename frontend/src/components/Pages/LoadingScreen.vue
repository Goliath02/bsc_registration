<template>
  <div class="loading-screen">
    <div class="spinner"></div>
    <p>{{ message }}</p>
  </div>
</template>

<script setup lang="ts">
import { bscBlack } from "@/utils/colors";
import { withDefaults, defineProps } from "vue";

interface Props {
  message?: string;
}

withDefaults(defineProps<Props>(), {
  message: "Daten werden geladen...",
});
</script>

<style scoped>
.loading-screen {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: v-bind(bscBlack);
  z-index: 9999;
}

.spinner {
  width: 50px;
  height: 50px;
  border: 4px solid #e0e0e0;
  border-top: 4px solid #42b983;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}

p {
  margin-top: 20px;
  color: #666;
  font-size: 16px;
}
</style>
