<script>
export default {
  name: "BscInput",

  props: {
    headerField: String,
    inputType: String,
    modelVar: [String, Date, Number]
  },

  data() {
    return {
      header: ""
    }
  },


}
</script>

<template>
  <div class="flex-1">
    <div class="font-bold text-[1.2em]">{{ headerField }}</div>
    <input v-bind="modelVar" @change="$emit(modelVar)" :type="inputType"
           class="w-full h-[3em] px-[0.5em] bg-[#585858] rounded-lg font-medium">
  </div>
</template>

<style scoped>

</style>