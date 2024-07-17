<script setup>
const modelValue = defineModel();
const props = defineProps({
	isNotValid: Boolean
})
</script>

<template>
	<div class="w-full">
		<div class="font-bold text-[1.2em]">Aus welchen Grund registrieren Sie die Person?</div>
		<select v-model="modelValue" :class="props.isNotValid ? 'border-2 border-red-700' : '' " class="w-full h-[3em] px-[1em]  bg-[#585858] rounded-lg font-medium" value="Bitte auswählen">
			<option>Nichtschwimmerkurs</option>
			<option>Schwimm-Kurs</option>
			<option>Wasserball</option>
		</select>
	</div>
</template>

<style scoped>

</style>