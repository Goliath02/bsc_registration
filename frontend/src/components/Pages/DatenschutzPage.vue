<script setup>

import FormHeader from "@/components/FormHeader.vue";
import Header from "@/components/BSC_Header.vue";
</script>

<template>
	<Header/>
  <main class="flex flex-1 justify-center items-center  lg:px-[7%] lg:py-[2%] min-h-0 ">
	  <div class="w-full h-full bg-bsc-gray rounded-lg overflow-hidden flex flex-col min-h-0 text-white">

      <FormHeader class="max-[640px]:text-lg" header-text="Datenschutzerklärung für die digitale Beitrittserklärung zum Schwimmverein"/>

      <div class="flex p-[1em] gap-[1em] max-[1024px]:flex-col overflow-y-auto">

        <div class="flex-1">

          <p>
            <span class="font-bold"> 1. Verantwortlicher</span>
            <span></span>
            <br>
	          Verantwortlich für die Verarbeitung Ihrer personenbezogenen Daten im Rahmen der digitalen Beitrittserklärung sind die Verantwortlichen gemäß dem nachträglich verlinkten Impressumshinweises.
	          <br>
	          <a class="text-blue-400" href="https://erster-bsc-pforzheim.de/impressum">https://erster-bsc-pforzheim.de/impressum</a>
          </p>
          <br>
          <p>
            <span class="font-bold">2. Zweck der Datenerhebung</span>
            <br>
	          Die Erhebung und Verarbeitung Ihrer personenbezogenen Daten erfolgt ausschließlich zur Bearbeitung und Verwaltung Ihrer Mitgliedschaft im Schwimmverein. Dazu gehören insbesondere:<br>
				&#x2022; Die Verwaltung der Mitgliederdaten<br>
				&#x2022; Die Kommunikation mit Mitgliedern<br>
				&#x2022; Die Erstellung und Pflege von Mitgliederlisten<br>
				&#x2022; DDer Bankeinzug des jeweilig fälligen Mitgliedsbeitrages<br>
	          Es werden keine weiteren Daten über den Beitrittsprozess hinaus verarbeitet.<br>
          </p>
          <br>
          <p>
            <span class="font-bold">3. Erhobene Daten</span>

            <br>
	          Im Rahmen der digitalen Beitrittserklärung werden folgende personenbezogene Daten erhoben und verarbeitet:<br>
	       &#x2022;  Name<br>
           &#x2022;Adresse<br>
           &#x2022;Geburtsdatum<br>
           &#x2022;E-Mail-Adresse<br>
           &#x2022;Telefonnummer<br>
           &#x2022;Notfallkontakt<br>
          </p>
          <br>

        </div>

        <div class="flex-1">

	        <p>
		        <span class="font-bold">4. Rechtsgrundlage der Verarbeitung</span>

		        <br>
		        DDie Verarbeitung Ihrer personenbezogenen Daten erfolgt auf Grundlage von Art. 6 Abs. 1 lit. b DSGVO (Vertragserfüllung) im Rahmen Ihrer Mitgliedschaft.
	        </p>
	        <br>
	        <p>
		        <span class="font-bold">6. Speicherung und Schutz von Daten</span>
		        <br>
		        Die erhobenen Daten werden elektronisch gespeichert und ausschließlich für die Mitgliederverwaltung genutzt. Der Zugriff ist nur autorisierten Personen gestattet. Technische und organisatorische Maßnahmen werden getroffen, um den Schutz Ihrer Daten vor Verlust, Missbrauch oder unbefugtem Zugriff sicherzustellen.
	        </p>

          <p>
            <span class="font-bold">7. Ihre Rechte</span>
            <br>
	          Sie haben gemäß der DSGVO folgende Rechte:
				<br>
	          &#x2022; Recht auf Auskunft über die gespeicherten Daten (Art. 15 DSGVO)<br>
	          &#x2022; Recht auf Berichtigung unrichtiger Daten (Art. 16 DSGVO)<br>
	          &#x2022; Recht auf Löschung Ihrer Daten (Art. 17 DSGVO), sofern keine gesetzlichen Aufbewahrungspflichten entgegenstehen<br>
	          &#x2022; Recht auf Einschränkung der Verarbeitung (Art. 18 DSGVO)<br>
	          &#x2022; Recht auf Widerspruch gegen die Verarbeitung (Art. 21 DSGVO)<br>
	          &#x2022; Recht auf Datenübertragbarkeit (Art. 20 DSGVO)<br>

	          Zur Ausübung Ihrer Rechte können Sie sich jederzeit an den Verantwortlichen wenden.

          </p>
          <br>

          <p>
            <span class="font-bold">8. Änderungen dieser Datenschutzrichtlinie</span>

            <br>
	          Wir behalten uns das Recht vor, diese Datenschutzerklärung anzupassen, um sie an gesetzliche Änderungen oder neue Funktionalitäten der Anwendung anzupassen.
          </p>
          <br>

          <p>
            <span class="font-bold">9. Kontakt</span>
            <br>
	          Bei Fragen zum Datenschutz oder zur Ausübung Ihrer Rechte wenden Sie sich bitte an
	          <a class="text-blue-400" href="mailto:datenschutz@erster-bsc-pforzheim.de">datenschutz@erster-bsc-pforzheim.de</a>
          </p>

        </div>

      </div>

      <div class="p-[1em]">
        <router-link to="/kontodaten">
          <button class="font-bold  px-[1em] py-[0.5em] rounded">Zurück</button>
        </router-link>
      </div>

    </div>
  </main>
</template>

<style scoped>

</style>