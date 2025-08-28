<script>
export default {
  name: "FormHeader",

  props: {
    headerText: String,
  },
};
</script>

<template>
  <h1
    class="w-full py-[0.5em] text-center font-bold text-[2em] text-white bg-[#141414]"
  >
    {{ headerText }}
  </h1>
</template>

<style scoped></style>
