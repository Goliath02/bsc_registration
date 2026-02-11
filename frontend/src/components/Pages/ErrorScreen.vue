<script setup lang="ts">
import { withDefaults, defineProps, defineEmits } from "vue";
import { bscBlack, bscRed, bscWhite } from "@/utils/colors";

interface Props {
  errorMessage?: string;
  title?: string;
  retryButtonText?: string;
}

interface Emits {
  (e: "retry"): void;
}

withDefaults(defineProps<Props>(), {
  errorMessage: "Backend ist nicht erreichbar",
  title: "Verbindungsfehler",
  retryButtonText: "Erneut versuchen",
});

const emit = defineEmits<Emits>();

const handleRetry = (): void => {
  emit("retry");
};
</script>

<template>
  <div class="error-screen">
    <div class="error-content">
      <div class="flex justify-center items-center error-icon">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          fill="currentColor"
          class="bi bi-exclamation-diamond-fill w-full h-full"
          viewBox="0 0 16 16"
        >
          <path
            d="M9.05.435c-.58-.58-1.52-.58-2.1 0L.436 6.95c-.58.58-.58 1.519 0 2.098l6.516 6.516c.58.58 1.519.58 2.098 0l6.516-6.516c.58-.58.58-1.519 0-2.098zM8 4c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 4.995A.905.905 0 0 1 8 4m.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2"
          />
        </svg>
      </div>
      <h2>{{ title }}</h2>
      <p>{{ errorMessage }}</p>
      <button @click="handleRetry" class="retry-button">
        {{ retryButtonText }}
      </button>
    </div>
  </div>
</template>

<style scoped>
.error-screen {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: v-bind(bscBlack);
  z-index: 9999;
  color: v-bind(bscWhite);
}

.error-content {
  text-align: center;
  padding: 40px;
  max-width: 400px;
}

.error-icon {
  width: 80px;
  height: 80px;
  color: v-bind(bscRed);
  margin: 0 auto 20px;
}

h2 {
  margin-bottom: 10px;
  font-size: 24px;
}

p {
  margin-bottom: 30px;
  font-size: 16px;
}

.retry-button {
  background-color: v-bind(bscRed);
  color: v-bind(bscBlack);
  border: none;
  padding: 12px 30px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s;
  font-weight: 500;
}

.retry-button:hover {
  background-color: #ea203c;
}

.retry-button:active {
  transform: scale(0.98);
}
</style>
