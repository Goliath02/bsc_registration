<script setup lang="ts">
import { useRegistrationStore } from "@/stores/RegistrationStore";

const store = useRegistrationStore();

const getPrice = () => {
  return store.calculatePrice.toFixed(2);
};
</script>

<template>
  <div class="flex justify-between items-center px-2 p-0 text-2xl h-min">
    {{ getPrice() }}€
  </div>
</template>

<style scoped></style>
