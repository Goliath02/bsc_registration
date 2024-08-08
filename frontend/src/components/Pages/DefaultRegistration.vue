<script>
import PeronenAuswahl from "@/components/BasicRegistration/PeronenAuswahl.vue";
import GrundAuwahl from "@/components/BasicRegistration/GrundAuwahl.vue";
import FormHeader from "@/components/FormHeader.vue";
import GenderSelection from "@/components/BasicRegistration/GenderSelection.vue";
import AddMemberButton from "@/components/BasicRegistration/AddMemberButton.vue";
import ReworkedBSCInput from "@/components/BasicRegistration/BSCInput.vue";
import ExtraPersonForm from "@/components/BasicRegistration/ExtraPersonForm.vue";
import {useRegistrationStore} from "@/stores/RegistrationStore.js";

export default {


	// TODO add studenten beweis

	name: "DefaultRegistration",
	components: {BSCInput: ReworkedBSCInput, ExtraPersonForm, AddMemberButton, GenderSelection, FormHeader, GrundAuwahl, PeronenAuswahl},

	data() {
		return {
			mainPerson: {
				type: "",
				reason: "",
				name: "",
				surename: "",
				birtday: "",
				gender: "",
				email: "",
				phone: "",
				street: "",
				plz: "",
				place: "",
			},
			morePerson: [],
		}
	},

	methods: {
		useRegistrationStore,
		addPersonForm() {
			let newPerson = {
				name: "",
				surename: "",
				birthday: "",
				gender: "",
			}

			useRegistrationStore().registrationData.morePersons.push(newPerson);
		},
	},
}
</script>

<template>

	<FormHeader header-text="Mitgliederregistrierung"/>

	<div class="flex flex-1 flex-col gap-[1em] max-h-[65vh] overflow-y-auto px-[2em] py-[1em] ">
		<PeronenAuswahl v-model.modelValue="useRegistrationStore().registrationData.mainData.type" @change="useRegistrationStore().updateValidation()" :is-not-valid="!useRegistrationStore().isFilled.defaultData.type && useRegistrationStore().triedToValidate"/>
		<GrundAuwahl v-model.modelValue="useRegistrationStore().registrationData.mainData.reason" @change="useRegistrationStore().updateValidation()" :is-not-valid="!useRegistrationStore().isFilled.defaultData.reason && useRegistrationStore().triedToValidate"/>

		<div class="flex gap-[1.5em]">
			<BSCInput v-model.modelValue="useRegistrationStore().registrationData.mainData.name" @input="useRegistrationStore().updateValidation()" :is-not-valid="!useRegistrationStore().isFilled.defaultData.name && useRegistrationStore().triedToValidate" header-field="Vorname"
			                  input-type="text"/>
			<BSCInput v-model.modelValue="useRegistrationStore().registrationData.mainData.surename" @input="useRegistrationStore().updateValidation()" :is-not-valid="!useRegistrationStore().isFilled.defaultData.surename && useRegistrationStore().triedToValidate" header-field="Nachname"
			                  input-type="text"/>
		</div>

		<div class="flex gap-[1.5em]">
			<BSCInput v-model.modelValue="useRegistrationStore().registrationData.mainData.birtday" @input="useRegistrationStore().updateValidation()" :is-not-valid="!useRegistrationStore().isFilled.defaultData.birthday && useRegistrationStore().triedToValidate" header-field="Geburtsdatum"
			                  input-type="date"/>
			<gender-selection v-model.modelValue="useRegistrationStore().registrationData.mainData.gender" @change="useRegistrationStore().updateValidation()" :is-not-valid="!useRegistrationStore().isFilled.defaultData.gender && useRegistrationStore().triedToValidate" class="flex-1 w-1/2"/>
		</div>

		<div class="flex gap-[1.5em] -sm:flex-col">
			<BSCInput v-model.modelValue="useRegistrationStore().registrationData.mainData.email" @input="useRegistrationStore().updateValidation()" :is-not-valid="!useRegistrationStore().isFilled.defaultData.email && useRegistrationStore().triedToValidate" header-field="Email"
			                  input-type="email"/>
			<BSCInput v-model.modelValue="useRegistrationStore().registrationData.mainData.phone" @input="useRegistrationStore().updateValidation()" :is-not-valid="!useRegistrationStore().isFilled.defaultData.phone && useRegistrationStore().triedToValidate" header-field="Teleon/Mobil"
			                  input-type="text"/>

		</div>

		<div class="flex gap-[1.5em] -sm:flex-col">
			<BSCInput v-model.modelValue="useRegistrationStore().registrationData.mainData.street" @input="useRegistrationStore().updateValidation()" :is-not-valid="!useRegistrationStore().isFilled.defaultData.street && useRegistrationStore().triedToValidate" header-field="Straße"
			                  input-type="text"/>
			<BSCInput v-model.modelValue="useRegistrationStore().registrationData.mainData.plz" @input="useRegistrationStore().updateValidation()" :is-not-valid="!useRegistrationStore().isFilled.defaultData.plz && useRegistrationStore().triedToValidate" header-field="PLZ"
			                  input-type="text"/>

		</div>

		<BSCInput v-model.modelValue="useRegistrationStore().registrationData.mainData.place" @input="useRegistrationStore().updateValidation()" :is-not-valid="!useRegistrationStore().isFilled.defaultData.place && useRegistrationStore().triedToValidate" header-field="Ort"
		                  input-type="text"/>

		<ExtraPersonForm v-for="(person, index) in useRegistrationStore().registrationData.morePersons"
		                 v-model.extraModelValues="useRegistrationStore().registrationData.morePersons[index]" :index="index"
		:input-data="{name: person.name, surename: person.surename, birthday: person.birthday, gender: person.gender}"/>

		<AddMemberButton @click="addPersonForm"/>

	</div>

</template>

<style scoped>

</style>