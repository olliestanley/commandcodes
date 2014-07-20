package com.github.dzineit.commandcodes.command.ccode;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.github.dzineit.commandcodes.CommandCodes;
import com.github.dzineit.commandcodes.code.CodeManager;
import com.github.dzineit.commandcodes.code.CommandCode;
import com.github.dzineit.commandcodes.command.CCodeSubCommand;
import com.github.dzineit.commandcodes.util.CommandUtil;

public final class CCodeShowCommand extends CCodeSubCommand {
	/**
	 * The plugin's code manager
	 */
	private final CodeManager codeMgr;

	public CCodeShowCommand(final CommandCodes plugin) {
		super(plugin);

		codeMgr = plugin.getCodeManager();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(final CommandSender sender, final String[] args) {
		if (args.length == 1) {
			sender.sendMessage(ChatColor.DARK_RED
					+ "Invalid syntax, /ccode show <code>");
		} else {
			int code;
			try {
				code = Integer.parseInt(args[1]);
			} catch (final NumberFormatException e) {
				sender.sendMessage(ChatColor.DARK_RED
						+ "Invalid code number, /ccode show <code>");
				code = 1000000;
			}

			if (code != 1000000) {
				CommandCode cc = codeMgr.getCurrentCommandCode(code);
				if (cc == null) {
					cc = codeMgr.getSpentCommandCode(code);
				}

				if (cc == null) {
					sender.sendMessage(ChatColor.DARK_RED
							+ "That code doesn't exist!");
				} else {
					sender.sendMessage(ChatColor.GOLD + "Code: " + cc.getCode());
					sender.sendMessage(ChatColor.GOLD + "Command: "
							+ cc.getCommand());
					sender.sendMessage(ChatColor.GOLD + "Uses: "
							+ cc.getAmount());
					sender.sendMessage(ChatColor.GOLD + "Remaining: "
							+ (cc.getAmount() - cc.getRedeemers().size()));
					sender.sendMessage(ChatColor.GOLD + "Redeemers: "
							+ CommandUtil.getPlayersStr(cc.getRedeemers()));
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String[] getNames() {
		return new String[] { "show", "info", "information" };
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getUsage() {
		return "/ccode show <code>";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDescription() {
		return "Shows details for the given code";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPermission() {
		return "commandcodes.show";
	}
}
