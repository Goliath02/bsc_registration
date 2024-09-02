<script setup>

import ReworkedBSCInput from "@/components/BasicRegistration/BSCInput.vue";
import GenderSelection from "@/components/BasicRegistration/GenderSelection.vue";
import {useRegistrationStore} from "@/stores/RegistrationStore.js";
import {computed, reactive} from "vue";

const extraModelValues = defineModel();
const props = defineProps({
	index: Number,
	inputData: Object
})

const isValid = reactive({
		name: false,
		surename: false,
		birthday: false,
		gender: false
	}
)
// a computed ref
const isValidName = computed(() => {
	return !(!props.inputData.name);
})

const isValidSurename = computed(() => {
	return !(!props.inputData.surename);
})

const isValidBirthday = computed(() => {

	console.log(props.inputData.birthday);

	return !(!props.inputData.birthday);
})

const isValidGender = computed(() => {
	return !(!props.inputData.gender);
})

</script>

<template>
	<div class="bg-bsc-lightgray p-[1em] rounded-lg">

		<div class="flex justify-between">
			<h3 class="font-bold text-[1.2em]">Extra Person {{ props.index + 1 }}</h3>
			<button class="fill-stone-400 hover:fill-stone-200" @click="useRegistrationStore().removeExtraPersonForm(props.index)">
				<svg class="bi bi-x-circle-fill" fill="currentColor" height="32" viewBox="0 0 16 16" width="32"
				     xmlns="http://www.w3.org/2000/svg">
					<path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0M5.354 4.646a.5.5 0 1 0-.708.708L7.293 8l-2.647 2.646a.5.5 0 0 0 .708.708L8 8.707l2.646 2.647a.5.5 0 0 0 .708-.708L8.707 8l2.647-2.646a.5.5 0 0 0-.708-.708L8 7.293z"/>
				</svg>
			</button>
		</div>

		<div class="flex gap-[1.5em]">
			<ReworkedBSCInput v-model.modelValue="extraModelValues.name"
			                  :is-not-valid="!isValidName && useRegistrationStore().triedToValidateBasicForm"
			                  header-field="Vorname" input-type="text" @input="useRegistrationStore().updateBasicValidation()"/>
			<ReworkedBSCInput v-model.modelValue="extraModelValues.surename"
			                  :is-not-valid="!isValidSurename && useRegistrationStore().triedToValidateBasicForm"
			                  header-field="Nachname" input-type="text" @input="useRegistrationStore().updateBasicValidation()"/>
		</div>

		<div class="flex gap-[1.5em]">
			<ReworkedBSCInput v-model.modelValue="extraModelValues.birthday"
			                  :is-not-valid="!isValidBirthday && useRegistrationStore().triedToValidateBasicForm"
			                  header-field="Geburtsdatum" input-type="date" @input="useRegistrationStore().updateBasicValidation()"/>
			<gender-selection v-model.modelValue="extraModelValues.gender"
			                  :is-not-valid="!isValidGender && useRegistrationStore().triedToValidateBasicForm"
			                  class="flex-1 w-1/2" @input="useRegistrationStore().updateBasicValidation()"/>

		</div>
	</div>
</template>

<style scoped>

</style>