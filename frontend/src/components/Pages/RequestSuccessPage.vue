<script>

export default {
	name: "RequestSuccessPage",
	components: {}
}
</script>

<template>
	<main class="flex flex-1 justify-center items-center lg:px-[7%] lg:py-[2%] min-h-0 bg-[url('@/assets//Water.jpg')] bg-cover overflow-y-auto ">
		<div class="lg:max-w-[50%] overflow-y-auto h-full bg-bsc-gray rounded-lg  flex flex-col min-h-0 text-white text-center gap-[2em] -lg:px-[1em] lg:px-[6em] pt-[3em] pb-[2em]">

				<img class=" h-[20em] px-[2em]" src="@/assets/icons/Full%20BSC%20Logo.svg">

				<h1 class="text-4xl font-bold">Vielen Dank für ihr Interesse!</h1>

			<div class="flex flex-col justify-center text-xl font-bold gap-[0.7em]">
				<p>Wir werden ihre Daten so schnell wie möglich bearbeiten</p>
				<p>Weitere Informationen wie Trainingszeiten und weiteres erhalten Sie in der Bestätigungs-email</p>
				<p>Wenn Sie weitere Fragen haben kontaktieren sie <a class="text-blue-500" href="mailto: test@test.com">test@test.com</a></p>


				<a href="/">
					<button class="border-white border font-bold  px-[1em] py-[0.5em] rounded">
						Zurück
					</button>
				</a>

			</div>
		</div>
	</main>
</template>

<style scoped>

</style>