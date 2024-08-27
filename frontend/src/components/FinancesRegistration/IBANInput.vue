<script>

import {useRegistrationStore} from "@/stores/RegistrationStore.js";

export default {
	name: "IBANInput",
	computed: {

		getIBAN() {
			return [this.IBANSection1, this.IBANSection2, this.IBANSection3, this.IBANSection4, this.IBANSection5, this.IBANSection6].join('-');
		},

		isValidGermanIBAN() {

			var s = [this.IBANSection2, this.IBANSection3, this.IBANSection4, this.IBANSection5, this.IBANSection6, this.IBANSection1].join('');

			s = s.replace("DE", "1314");

			return useRegistrationStore().triedToValidateFinancialForm && !(BigInt(s) % 97n === BigInt(1));
		},

	},

	props: {
		isNotValid: Boolean
	},

	data() {
		return {
			IBANSection1: null,
			IBANSection2: null,
			IBANSection3: null,
			IBANSection4: null,
			IBANSection5: null,
			IBANSection6: null,


			// IBANSection1: "DE80",
			// IBANSection2: "5001",
			// IBANSection3: "0517",
			// IBANSection4: "5408",
			// IBANSection5: "3325",
			// IBANSection6: "01",
		}
	},

	methods: {

		onIbanInput(event,max) {
			useRegistrationStore().registrationData.financial.iban = this.getIBAN;
			this.focusNext(event, max);
		},


		focusNext(event, max) {
    useRegistrationStore().updateFinancialValidation();
			if (event.target.value.length === max) {
				const nextElement = this.$refs?.[`input-${Number(event.target.dataset.index) + 1}`]
				if (nextElement) {
					nextElement.focus()
				}
			}
		},


	}
}
</script>

<template>

	<div>
		<div class="font-bold text-[1.2em]">IBAN</div>
		<div class="flex gap-[1em] -lg:gap-[0.5em]">
			<input ref="input-1" v-model="IBANSection1"
			       :class="isValidGermanIBAN ? 'border-2 border-red-700' : '' "
			       class="w-full flex-2 h-[3em] lg:px-[0.5em]  bg-[#585858] rounded-lg font-medium appearance-none text-center"
			       data-index="1"
			       maxlength="4"
			       @input="onIbanInput($event,4)"
			       >
			<input ref="input-2" v-model="IBANSection2"
			       :class="isValidGermanIBAN ? 'border-2 border-red-700' : '' "
			       class="w-full flex-2 h-[3em] lg:px-[0.5em]  bg-[#585858] rounded-lg font-medium appearance-none text-center"
			       data-index="2"
			       maxlength="4"
			       @input="onIbanInput($event,4)"
			       >
			<input ref="input-3" v-model="IBANSection3"
			       :class="isValidGermanIBAN ? 'border-2 border-red-700' : '' "
			       class="w-full flex-2 h-[3em] lg:px-[0.5em]  bg-[#585858] rounded-lg font-medium appearance-none text-center"
			       data-index="3"
			       maxlength="4"
			       @input="onIbanInput($event,4)"
			       >
			<input ref="input-4" v-model="IBANSection4"
			       :class="isValidGermanIBAN ? 'border-2 border-red-700' : '' "
			       class="w-full flex-2 h-[3em] lg:px-[0.5em]  bg-[#585858] rounded-lg font-medium appearance-none text-center"
			       data-index="4"
			       maxlength="4"
			       @input="onIbanInput($event,4)"
			       >
			<input ref="input-5" v-model="IBANSection5"
			       :class="isValidGermanIBAN ? 'border-2 border-red-700' : '' "
			       class="w-full flex-2 h-[3em] lg:px-[0.5em]  bg-[#585858] rounded-lg font-medium appearance-none text-center"
			       data-index="5"
			       maxlength="4"
			       @input="onIbanInput($event,4)"
			       >

			<input ref="input-6" v-model="IBANSection6"
			       :class="isValidGermanIBAN ? 'border-2 border-red-700' : '' "
			       class="w-1/2 flex-2  h-[3em] lg:px-[0.5em]  bg-[#585858] rounded-lg font-medium appearance-none text-center"
			       data-index="6"
			       maxlength="2"
			       @input="onIbanInput($event,2)"
			       >

		</div>
	</div>

</template>

<style scoped>

</style>