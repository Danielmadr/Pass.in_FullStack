import { useState } from "react";
import { View, Image, StatusBar, Alert } from "react-native";
import { MaterialCommunityIcons } from "@expo/vector-icons";
import { Link } from "expo-router";

import { Input } from "@/components/input";
import { Button } from "@/components/button";

import { api } from "@/server/api";

import { colors } from "@/styles/colors";

export default function Home() {
  const [value, setValue] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  async function handleAccessCredential() {
    try {
      if (!value.trim()) {
        return Alert.alert(
          "Atenção",
          "Por favor, informe o código do ingresso."
        );
      }

      setIsLoading(true);

      const { data } = await api.get(`/attendees/${value}/badge`);
      console.log(data.badge);
    } catch (error) {
      console.log(error);
      Alert.alert("Atenção", "Credencial não encontrada");
    } finally {
      setIsLoading(false);
    }
  }

  return (
    <View className="flex-1 bg-green-500 items-center justify-center p-8">
      <StatusBar barStyle="light-content" />
      <Image
        source={require("@/assets/logo.png")}
        className="h-16"
        resizeMode="contain"
      />

      <View className="w-full mt-12 gap-3">
        <Input>
          <MaterialCommunityIcons
            name="ticket-confirmation-outline"
            size={20}
            eeeee
            color={colors.green[200]}
          />
          <Input.Field
            placeholder="Código do ingresso"
            onChangeText={setValue}
          />
        </Input>

        <Button title="Acessar credencial" onPress={handleAccessCredential} />

        <Link
          href="/register"
          className="text-gray-100 text-base font-bold text-center mt-2"
        >
          Ainda não possui ingresso?
        </Link>
      </View>
    </View>
  );
}
