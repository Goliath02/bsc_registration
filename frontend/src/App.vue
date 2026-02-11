<script setup lang="ts">
import { useRegistrationStore } from "@/stores/RegistrationStore.js";
import ResponseModal from "@/components/ResponseModal.vue";
import { onMounted } from "vue";
import { InitialData, useAppState } from "@/service/AppStateService";
import api from "@/service/Api";
import LoadingScreen from "@/components/Pages/LoadingScreen.vue";
import ErrorScreen from "@/components/Pages/ErrorScreen.vue";

const { isLoading, hasError, errorMessage, setLoading, setError, reset } =
  useAppState();

const initializeApp = async (): Promise<void> => {
  reset();
  setLoading(true);

  try {
    const [config] = await Promise.all([
      api.get<any>("/config"),
    ]);

    const initialData: InitialData = {
      config: config.data,
    };
    setLoading(false);
  } catch (error) {
    console.error("Initialisierung fehlgeschlagen:", error);
  }
};

onMounted((): void => {
  initializeApp();
});
</script>

<template>
  <div class="bg-black h-screen flex flex-col">
    <LoadingScreen v-if="isLoading" />
    <ErrorScreen
      v-else-if="hasError"
      :error-message="errorMessage"
      @retry="initializeApp"
    />
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
