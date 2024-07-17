<script>
export default {
  name: "MainPage"
}
</script>

<template>
  <div class="h-full flex text-white ">

    <div class="flex justify-center items-center flex-1 bg-[url('@/assets//Water.jpg')] bg-cover">
      <img class="w-[25em] px-[2em]" src="@/assets/icons/Full%20BSC%20Logo.svg">
    </div>

    <!--    TODO Unterschachtelung erstellen zur änderung von innere Box-->

    <div class="flex-1 flex flex-col">

      <div>
        <h1 class="w-full py-[0.5em] text-center font-bold text-[2em] bg-[#141414]">Mitgliederregistrierung</h1>
        <div class="w-full p-[1em] font-bold text-[1.2em]">
          <div>Wen möchten Sie anmelden?</div>
          <select class="bg-[#585858]" value="Bitte auswählen">
            <option>Erwachsener</option>
            <option>Kind</option>
            <option>Schüler/Student über 18</option>
          </select>
        </div>
      </div>

      <div></div>

    </div>
  </div>
</template>

<style scoped>

</style>