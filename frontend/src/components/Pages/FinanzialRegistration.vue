<script setup >

import {yupResolver} from "@primevue/forms/resolvers/yup";
import * as yup from "yup";
import {useRegistrationStore} from "@/stores/RegistrationStore";
import FormHeader from "@/components/FormHeader.vue";
import IBANInput from "@/components/FinancesRegistration/IBANInput.vue";
import {Form} from "@primevue/forms";
import router from "@/router.js";
import {ref} from "vue";

const resolver = yupResolver(yup.object().shape({
    "iban": yup.string()

        .test("is-valid-iban", "Ungültige IBAN", (value) =>
            isValidGermanIBAN(value)
        ),
    "nameOfBankOwner": yup.string().required("Name wird benötigt."),
    "sureNameBankOwner": yup.string().required("Nachname wird benötigt."),
    "dataProtection": yup.boolean().oneOf([true], "Zustimmung der Datenschutzbestimmungen ist erforderlich."),
    "dataCorrectness": yup.boolean().oneOf([true], "Zustimmung der Datenschutzbestimmungen ist erforderlich."),
    "dataStatute": yup.boolean().oneOf([true], "Erkennung der Satzung des Vereins ist erforderlich."),
}));

const isValidGermanIBAN = (iban) => {

    iban = useRegistrationStore().registrationData.financial.iban;

	// deutsche IBANs sind immer 22 Zeichen und starten mit "DE"
	const validIbanLength = iban.length === 22 && iban.startsWith("DE");

	if (!validIbanLength) {
		return false;
	}

	// Schritt 1: erste 4 Zeichen nach hinten verschieben
	const rearranged = iban.slice(4) + iban.slice(0, 4);

	// Schritt 2: alle Buchstaben in Zahlen konvertieren (A=10, ..., Z=35)
	let numeric = "";
	for (const ch of rearranged) {
		if (/[A-Z]/.test(ch)) {
			numeric += (ch.charCodeAt(0) - 55).toString();
		} else {
			numeric += ch;
		}
	}

	// Schritt 3: BigInt modulo 97 prüfen
	let validIban;
	try {
		validIban = BigInt(numeric) % 97n === 1n;
	} catch (e) {
		console.error("IBAN konnte nicht geprüft werden:", e);
		validIban = false;
	}

	return validIban && validIbanLength;
};


const ibanErrorMessage = ref("");

const onFormSubmit = (values) => {

	if (values.errors.iban) {
		ibanErrorMessage.value = values?.errors?.iban[0];
	}

    if (values.valid) {
        router.push("/zusammenfassung");
    }
}

</script>

<template>

	<FormHeader header-text="Mitgliederregistrierung"/>

	<Form v-slot="$form" :initialValues="useRegistrationStore().registrationData.financial" id="financialRegistrationForm"
	      :resolver="resolver" @submit="onFormSubmit" class="flex flex-1 flex-col gap-[1em] max-h-[65vh] overflow-y-auto px-[2em] py-[1em]">

			<i-b-a-n-input name="iban" v-model="useRegistrationStore().registrationData.financial.iban"/>
			<Message v-if="$form.iban?.invalid" severity="error"  variant="simple"> {{ $form.iban.error.message }}</Message>


		<div class="flex gap-4">

			<FloatLabel variant="in" class="w-full">
				<InputText class="w-full" inputId="dd-accountName" name="nameOfBankOwner"
				           v-model="useRegistrationStore().registrationData.financial.nameOfBankOwner"/>
				<label for="dd-accountName">
					Vorname des Kontoinhabers
				</label>
				<Message v-if="$form.nameOfBankOwner?.invalid" severity="error" size="small" variant="simple">{{ $form.nameOfBankOwner.error.message }}</Message>
			</FloatLabel>

			<FloatLabel variant="in" class="w-full">
				<InputText class="w-full" inputId="dd-accountSureName" name="sureNameBankOwner"
				           v-model="useRegistrationStore().registrationData.financial.sureNameBankOwner"/>
				<label for="dd-accountSureName">
					Nachname des Kontoinhabers
				</label>
				<Message v-if="$form.sureNameBankOwner?.invalid" severity="error" size="small" variant="simple">{{ $form.sureNameBankOwner.error.message }}</Message>
			</FloatLabel>

		</div>

		<div class="flex items-center gap-4">
			<Checkbox inputId="dd-dataProtection" name="dataProtection" v-model="useRegistrationStore().registrationData.dataProtection"
			          binary/>
			<label for="dd-dataProtection">
				<p>Hiermit stimme ich den
					<router-link to="/datenschutz" class="text-blue-600 underline">Datenschutzrichtlinien</router-link>
					zu.
				</p>
			</label>
		</div>

		<div class="flex items-center gap-4">
			<Checkbox inputId="dd-dataCorectness" name="dataCorrectness" v-model="useRegistrationStore().registrationData.correctness"
			          binary/>
			<label for="dd-dataCorectness">
				<p>
					Hiermit bestätige ich, dass alle angegebenen Daten der Richtigkeit ensprechen.
				</p>
			</label>
		</div>

		<div class="flex items-center gap-4">
			<Checkbox inputId="dd-dataStatute" name="dataStatute" v-model="useRegistrationStore().registrationData.statute" binary/>
			<label for="dd-dataStatute">
				<p>
					Ich erkenne die <a href="">Satzung</a> des Vereins an und verpflichte mich zur Zahlung des Mitgliedsbeitrags.
				</p>
			</label>
		</div>

		<input v-model="useRegistrationStore().registrationData.hiddenSecurityCheck" class="invisible h-0" type="checkbox">

	</Form>

</template>

<style scoped>

</style>