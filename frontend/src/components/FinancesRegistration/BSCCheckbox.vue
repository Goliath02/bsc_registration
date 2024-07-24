<script>
export default {
  name: "BSCCheckbox",

  props: {
    labelText: String
  }
}
</script>

<template>
  <div class="flex gap-[0.5em]">
    <input name="datenschutzCheckbox" id="bscCehckbox" type="checkbox" class="min-w-[1.5em] min-h-[1.5em]">

    <label for="bscCehckbox" class="text-white font-bold text-[1.2em]">{{ labelText }}</label>

  </div>
</template>

<style scoped>

</style>