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
		  <input v-model="modelValue" name="datenschutzCheckbox" id="datenschutzCheckbox" type="checkbox"
		          class="min-w-[1.5em] min-h-[1.5em]">
	  </div>

    <label for="datenschutzCheckbox" class="text-white font-bold text-[1.2em]">
      <p>Hiermit stimme ich den
        <router-link to="/datenschutz" class="text-blue-600 underline">Datenschutzrichtlinien</router-link>
        zu.
      </p>
    </label>

  </div>

</template>

<style scoped>

</style>