<script>
import PeronenAuswahl from "@/components/BasicRegistration/PeronenAuswahl.vue";
import FormHeader from "@/components/FormHeader.vue";
import GrundAuwahl from "@/components/BasicRegistration/GrundAuwahl.vue";
import GenderSelection from "@/components/BasicRegistration/GenderSelection.vue";
import IBANInput from "@/components/FinancesRegistration/IBANInput.vue";
import BSCCheckbox from "@/components/FinancesRegistration/BSCCheckbox.vue";
import DatenschutzCheckbox from "@/components/FinancesRegistration/DatenschutzCheckbox.vue";
import ReworkedBSCInput from "@/components/BasicRegistration/BSCInput.vue";
import {useRegistrationStore} from "@/stores/RegistrationStore.js";

export default {
	name: "FinanzialRegistration",
	components: {
		BSCInput: ReworkedBSCInput,
		DatenschutzCheckbox, BSCCheckbox, IBANInput, GenderSelection, GrundAuwahl, FormHeader, PeronenAuswahl
	},
	data() {
		return {

			financial: {
				iban: "",
				bankOwnerName: "",
				bankOwnerSureName: "",
			},

			datenschutz: "",
			richtigkeit: "",
			hiddenSecurityCheck: ""
		}
	},

	methods: {
		useRegistrationStore
	},

}
</script>

<template>

	<FormHeader header-text="Mitgliederregistrierung"/>

	<div class="flex flex-1 flex-col gap-[1em] max-h-[65vh] overflow-y-auto px-[2em] py-[1em]">
		<div class="flex gap-[1em]">

		</div>

		<i-b-a-n-input/>

		<div class="flex gap-[1em]">
			<BSCInput v-model.modelValue="useRegistrationStore().registrationData.financial.nameOfBankOwner"
			          :is-not-valid="!useRegistrationStore().isFilled.financialData.nameOfBankOwner && useRegistrationStore().triedToValidateFinancialForm"
			          header-field="Vorname des Kontoinhabers"
			          input-type="text"
			          @input="useRegistrationStore().updateFinancialValidation()"/>

			<BSCInput v-model.modelValue="useRegistrationStore().registrationData.financial.sureNameBankOwner"
			          :is-not-valid="!useRegistrationStore().isFilled.financialData.surenameOfBankOwner && useRegistrationStore().triedToValidateFinancialForm"
			          header-field="Nachname des Kontoinhabers"
			          input-type="text"
			          @input="useRegistrationStore().updateFinancialValidation()"/>

		</div>

		<datenschutz-checkbox v-model="useRegistrationStore().registrationData.dataProtection"
		                      :is-not-valid="!useRegistrationStore().isFilled.dataProtection && useRegistrationStore().triedToValidateFinancialForm"
		                      @change="useRegistrationStore().updateFinancialValidation()"/>

		<b-s-c-checkbox v-model="useRegistrationStore().registrationData.correctness"
		                :is-not-valid="!useRegistrationStore().isFilled.correctness && useRegistrationStore().triedToValidateFinancialForm"
		                label-text="Hiermit bestätige ich, dass alle angegebenen Daten der Richtigkeit ensprechen."
		                @change="useRegistrationStore().updateFinancialValidation()"/>

		<input v-model="useRegistrationStore().registrationData.hiddenSecurityCheck" class="invisible h-0" type="checkbox">

	</div>

</template>

<style scoped>

</style>