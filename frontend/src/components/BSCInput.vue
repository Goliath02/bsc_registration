<script>
export default {
  name: "TextInput",

  props:{
    headerField: String,
    inputType: String,
  }
}
</script>

<template>
  <div class="flex-1">
    <div class="font-bold text-[1.2em]">{{headerField}}</div>
    <input :type="inputType" class="w-full h-[3em] px-[0.5em]  bg-[#585858] rounded-lg font-medium">
  </div>
</template>

<style scoped>

</style>