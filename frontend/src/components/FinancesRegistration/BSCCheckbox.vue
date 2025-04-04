<script setup>
const modelValue = defineModel();
const props = defineProps({
  labelText: String,
  isNotValid: Boolean

})
</script>

<template>
  <div class="flex gap-[0.5em]">
	  <div class="h-min flex content-center items-center" :class="props.isNotValid ? 'outline outline-2 outline-red-700' : '' ">
		  <input v-model="modelValue" name="datenschutzCheckbox" :id="labelText" type="checkbox"
		          class="min-w-[1.5em] min-h-[1.5em]">
	  </div>

    <label :for="labelText" class="text-white font-bold text-[1.2em]">{{ labelText }}</label>

  </div>
</template>

<style scoped>

</style>