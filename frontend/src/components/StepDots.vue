<template>
  <div class="flex basis-1/3 justify-center items-center gap-[1em]">
    <div
      v-for="page in pages"
      :class="{ '!bg-white': $route.name === page }"
      class="w-[0.8em] h-[0.8em] bg-stone-500 rounded-full transition-colors dot"
    ></div>
  </div>
</template>
<script>
export default {
  name: "StepDots",
  props: {
    pages: {},
  },
};
</script>
<style scoped>
.dot {
  transition-property: color, background-color, border-color,
    text-decoration-color, fill, stroke;
  transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
  transition-duration: 150ms;
}
</style>
