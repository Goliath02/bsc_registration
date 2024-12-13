<script setup>
const modelValue = defineModel();
const props = defineProps({
	isNotValid: Boolean
})
</script>

<template>
	<div>
		<div class="font-bold text-[1.2em]">Geschlecht</div>
		<select v-model="modelValue" :class="props.isNotValid ? 'border-2 border-red-700' : '' "
		        class="w-full h-[3em] px-[1em]  bg-[#585858] rounded-lg font-medium" value="Bitte auswählen">
			<option>Männlich</option>
			<option>Weiblich</option>
			<option>Divers</option>
		</select>
	</div>
</template>

<style scoped>

</style>