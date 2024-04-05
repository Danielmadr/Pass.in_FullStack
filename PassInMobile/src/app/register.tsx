import { useState } from "react";
import { View, Image, StatusBar, Alert } from "react-native";
import { FontAwesome6, MaterialIcons } from "@expo/vector-icons";
import { Link, router } from "expo-router";

import { Input } from "@/components/input";
import { Button } from "@/components/button";

import { colors } from "@/styles/colors";

import { api } from "@/server/api";
import axios from "axios";

const EVENT_ID = "d094f1a1-e518-4f2f-9411-bbd845c1ee37";

export default function Register() {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  async function handleRegister() {
    try {
      if (!name.trim() || !email.trim()) {
        return Alert.alert("Atenção", "Por favor, preencha todos os campos.");
      }

      setIsLoading(true);

      const registerResponse = await api.post(`/events/${EVENT_ID}/attendees`, {
        name,
        email,
      });

      if (registerResponse.data.attendeeId) {
        Alert.alert("Sucesso", "Inscricão realizada com sucesso", [
          {
            text: "Ir para minha Credencial",
            onPress: () => {
              router.push("/ticket");
            },
          },
        ]);
      }

      router.push("/ticket");
    } catch (error) {
      console.log(error);

      if (axios.isAxiosError(error)) {
        if (error.response?.status === 409) {
          return Alert.alert("Atenção", "Inscricão ja existente");
        }
      }

      Alert.alert("Atenção", "Falha ao realizar inscricão");
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
          <FontAwesome6
            name="user-circle"
            size={20}
            color={colors.green[200]}
          />
          <Input.Field placeholder="Nome Completo" onChangeText={setName} />
        </Input>

        <Input>
          <MaterialIcons
            name="alternate-email"
            size={20}
            color={colors.green[200]}
          />

          <Input.Field
            placeholder="E-mail"
            keyboardType="email-address"
            onChangeText={setEmail}
          />
        </Input>

        <Button
          title="Realizar Inscrição"
          onPress={handleRegister}
          isLoading={isLoading}
        />
        <Link
          href="/"
          className="text-gray-100 text-base font-bold text-center mt-2"
        >
          Já possui ingresso?
        </Link>
      </View>
    </View>
  );
}
