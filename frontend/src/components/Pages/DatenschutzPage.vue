<script setup>

import FormHeader from "@/components/FormHeader.vue";
</script>

<template>
	<main class="flex flex-1 justify-center items-center  lg:px-[7%] lg:py-[2%] min-h-0 ">
		<div class="w-full h-full bg-bsc-gray rounded-lg overflow-hidden flex flex-col min-h-0 text-white">

			<FormHeader header-text="Datenschutzrichtlinien"/>

			<div class="flex p-[1em] gap-[1em] -lg:flex-col overflow-y-auto">

				<div class="flex-1">

					<p>
						<span class="font-bold"> 1. Verantwortlicher</span>
						<span></span>
						<br>
						Verantwortlich für die Erhebung, Verarbeitung und Nutzung Ihrer personenbezogenen Daten im Sinne der
						Datenschutz-Grundverordnung (DSGVO) ist:
						Schwimmverein [Name] [Adresse] [E-Mail] [Telefonnummer]
					</p>
					<br>
					<p>
						<span class="font-bold">2. Zweck der Datenerhebung</span>

						<br>
						Wir erheben und verarbeiten Ihre personenbezogenen Daten ausschließlich zur Verwaltung und Pflege unserer
						Mitgliederliste sowie zur Kommunikation mit unseren Mitgliedern. Diese Daten werden insbesondere benötigt für:
						Die Registrierung und Verwaltung Ihrer Mitgliedschaft.
						Die Zusendung von Informationen und Mitteilungen rund um den Schwimmverein.
					</p>
					<br>
					<p>
						<span class="font-bold">3. Erhobene Daten</span>

						<br>
						Im Rahmen der Registrierung erheben wir die folgenden personenbezogenen Daten:
						Name
						Adresse
						Geburtsdatum
						E-Mail-Adresse
						Telefonnummer
						Notfallkontakt
					</p>
					<br>
					<p>
						<span class="font-bold">4. Datenübermittlung und -speicherung</span>

						<br>
						Die erhobenen Daten werden per E-Mail an das jeweilige Mitglied zur Überprüfung und Pflege der Mitgliederliste
						versandt. Anschließend werden die Daten in eine Excel-Liste eingetragen, die auf einem passwortgeschützten Computer
						gespeichert wird.
					</p>
					<br>
					<p>
						<span class="font-bold">5. Weitergabe von Daten</span>

						<br>
						Eine Weitergabe Ihrer personenbezogenen Daten an Dritte erfolgt grundsätzlich nicht, außer in folgenden Fällen:
						Mit Ihrer ausdrücklichen Einwilligung.
						Wenn eine gesetzliche Verpflichtung zur Weitergabe besteht.
					</p>

				</div>

				<div class="flex-1">

					<p>
						<span class="font-bold">6. Sicherheit Ihrer Daten</span>

						<br>
						Wir treffen technische und organisatorische Maßnahmen, um Ihre personenbezogenen Daten vor Verlust, Missbrauch und
						unbefugtem Zugriff zu schützen. Unsere Sicherheitsmaßnahmen werden entsprechend der technologischen Entwicklung
						fortlaufend verbessert.
					</p>
					<br>
					<p>
						<span class="font-bold">7. Ihre Rechte</span>

						<br>
						Sie haben das Recht:
						Auskunft über Ihre bei uns gespeicherten Daten zu erhalten.
						Die Berichtigung unrichtiger Daten zu verlangen.
						Die Löschung Ihrer Daten zu verlangen, sofern keine gesetzlichen Aufbewahrungspflichten entgegenstehen.
						Die Einschränkung der Verarbeitung Ihrer Daten zu verlangen.
						Der Verarbeitung Ihrer Daten zu widersprechen.
						Ihre Einwilligung zur Datenverarbeitung jederzeit zu widerrufen.
						Zur Ausübung dieser Rechte wenden Sie sich bitte an die oben genannten Kontaktdaten.
					</p>
					<br>

					<p>
						<span class="font-bold">8. Änderungen dieser Datenschutzrichtlinie</span>

						<br>
						Wir behalten uns das Recht vor, diese Datenschutzrichtlinie anzupassen, damit sie stets den aktuellen rechtlichen
						Anforderungen entspricht oder um Änderungen unserer Leistungen in der Datenschutzrichtlinie umzusetzen. Für Ihren
						erneuten Besuch gilt dann die neue Datenschutzrichtlinie.
					</p>
					<br>

					<p>
						<span class="font-bold">9. Kontakt</span>
						<br>
						Bei Fragen zum Datenschutz oder zur Ausübung Ihrer Rechte kontaktieren Sie uns bitte unter:
						Schwimmverein [Name] [Adresse] [E-Mail] [Telefonnummer]
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