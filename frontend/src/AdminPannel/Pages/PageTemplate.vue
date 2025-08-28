<script setup lang="ts">
const props = defineProps({
  title: String,
  actionButton: Object,
});
</script>

<template>
  <main class="flex flex-col max-h-screen h-[92vh] md:h-[98vh]">
    <div class="flex justify-between mx-8 mt-4">
      <h2 class="text-2xl font-bold">Courses</h2>
    </div>

    <div
      class="bg-bsc-gray overflow-y-auto rounded-lg p-4 flex gap-4 m-4 flex-1 items-center-safe justify-center-safe"
    >
      <slot></slot>
    </div>
  </main>
</template>

<style scoped></style>
