<script setup>
const modelValue = defineModel();
const props = defineProps({
	labelText: String,
	isNotValid: Boolean

})
</script>

<template>
	<div class="flex gap-[0.5em]">
		<input v-model="modelValue" name="datenschutzCheckbox" id="bscCehckbox" type="checkbox"
		       :class="props.isNotValid ? 'outline outline-2 outline-red-700' : '' " class="min-w-[1.5em] min-h-[1.5em]">

		<label for="bscCehckbox" class="text-white font-bold text-[1.2em]">{{ labelText }}</label>

	</div>
</template>

<style scoped>

</style>