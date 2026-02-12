<script setup lang="ts">

import { MemberRegistrationStore } from "@/stores/MemberRegistrationStore";

const store = MemberRegistrationStore();

const getPrice = () => {
  return store.price.toFixed(2);
};
</script>

<template>
  <div class="flex justify-between items-center px-2 p-0 text-2xl h-min">
    {{ getPrice() }}€
  </div>
</template>

<style scoped></style>
