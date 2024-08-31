<script setup>
const modelValue = defineModel();
const props = defineProps({
	headerField: String,
	inputType: String,
	isNotValid: Boolean
})
</script>

<template>
	<div class="flex-1">
		<div class="font-bold text-[1.2em]">{{ props.headerField }}</div>
		<input v-model="modelValue" :type="props.inputType" :class="props.isNotValid ? 'border-2 border-red-700' : '' "
		       class="w-full h-[3em] px-[0.5em] bg-[#585858] rounded-lg font-medium">
	</div>
</template>

<style scoped>

</style>